package com.flatRock.project.sso.service;

import com.flatRock.project.sso.configs.JwtTokenUtil;
import com.flatRock.project.sso.enums.Role;
import com.flatRock.project.sso.exceptions.BadCredentialsException;
import com.flatRock.project.sso.feign.models.User;
import com.flatRock.project.sso.model.JwtRequest;
import com.flatRock.project.sso.model.UserInformation;
import com.flatRock.project.sso.repositories.UserRepository;
import com.flatRock.project.sso.session.model.SessionEntity;
import com.flatRock.project.sso.session.service.SessionService;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Service
public class SsoService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final SessionService sessionService;

    private final PasswordEncoder passwordEncoder;

    public SsoService(UserRepository userRepository, AuthenticationManager authenticationManager,
                      JwtTokenUtil jwtTokenUtil, SessionService sessionService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.sessionService = sessionService;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, Object> getUserIdAndRole(String token) {
        return jwtTokenUtil.getUserIdAndRoleFromToken(token);
    }

    public UserInformation isTokenActive(final String token) {
        Boolean isExpired = jwtTokenUtil.isTokenExpired(token);
        if (!isExpired) {
            String email = jwtTokenUtil.getEmailFromToken(token);
            Role roles = (Role) jwtTokenUtil.getAllClaimsFromToken(token).get("role");
            User user = userRepository.findByEmail(email);
            return new UserInformation(user.getUsername(), user.getFirstName(), user.getLastName(), email, user.getId(), token, roles);

        }
        return new UserInformation();
    }

    public UserInformation login(JwtRequest authenticationRequest) throws Exception {
        return getClientInformationForClient(authenticationRequest, authenticationRequest.getEmail());

    }

    private UserInformation getClientInformationForClient(JwtRequest authenticationRequest, String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new BadCredentialsException("invalid credentials");
        }
        String password = user.getPassword();
        if (passwordEncoder.matches(authenticationRequest.getPassword(), password)) {
            return getResponseEntity(authenticationRequest);
        } else {
            throw new BadCredentialsException("invalid credentials");
        }
    }

    public String generateToken(@RequestParam String email) {
        UserDetails userDetails = loadUserByUsername(email);
        String token = jwtTokenUtil.generateToken(userDetails);
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
        SessionEntity sessionEntity = createSessionEntityFromClaims(claims);
        sessionService.addWithTokenKey(token, sessionEntity);
        return token;
    }

    private SessionEntity createSessionEntityFromClaims(Claims claims) {
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setUserId(claims.get("userId").toString());
        if (claims.get("username") != null) {
            sessionEntity.setUsername(claims.get("username").toString());
        }
        sessionEntity.setEmail(claims.get("email").toString());
        sessionEntity.setLastUpdatedDate(new Date().toInstant().getEpochSecond());
        return sessionEntity;
    }

    private UserInformation getResponseEntity(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
        final Long userId = (Long) claims.get("userId");
        createSession(token, userId, claims);
        String email = userDetails.getUsername();
        return getClientInformation(token, userId, email);
    }

    private void createSession(String token, Long userId, Claims claims) {
        long date = new Date().toInstant().getEpochSecond();
        final String userEmail = claims.get("email").toString();
        String username = null;
        if (claims.get("username") != null) {
            username = claims.get("username").toString();
        }
        sessionService.addWithTokenKey(token, new SessionEntity(userId.toString(), username, userEmail, date));
        sessionService.addWithUserIdKey(userId.toString(), token);
    }

    private UserInformation getClientInformation(String token, Long userId, String email) {
        User user = userRepository.findByEmail(email);
        return new UserInformation(user.getUsername(), user.getFirstName(), user.getLastName(), email, userId, token, user.getRole());
    }

    private void authenticate(String userName, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
    }
}
