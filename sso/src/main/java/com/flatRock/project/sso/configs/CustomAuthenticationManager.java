package com.flatRock.project.sso.configs;

import com.flatRock.project.sso.enums.Role;
import com.flatRock.project.sso.service.SsoService;
import io.jsonwebtoken.Claims;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Setter
    private SsoService ssoService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String authToken = authentication.getCredentials().toString();
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(authToken);
        String username = claims.get("username").toString();
        UserDetails userDetails = ssoService.loadUserByUsername(username);
        jwtTokenUtil.validateToken(authToken, userDetails);
        Role role = claims.get("role", Role.class);
        return new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(new SimpleGrantedAuthority(role.toString())));
    }
}
