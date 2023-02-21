package com.flatRock.project.sso.configs;

import com.flatRock.project.sso.feign.models.User;
import com.flatRock.project.sso.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private final UserRepository userRepository;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public JwtTokenUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${jwt.secret}")
    private String secret;

    @Value("${token.expiration.time}")
    private long tokenExpirationTime;

    //get email form token
    public String getEmailFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("email").toString();
    }

    //get email form token
    public String getUsernameFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("username").toString();
    }

    //retrieve expiration date from jwt token
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //get userId form token
    public String getUserIdFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("userId").toString();
    }

    //get userId and role form token
    public Map<String, Object> getUserIdAndRoleFromToken(String token) {
        Map<String, Object> userInfo = new HashMap<>();
        final Claims claims = getAllClaimsFromToken(token);
        userInfo.put("userId", claims.get("userId"));
        userInfo.put("role", claims.get("role"));
        return userInfo;
    }

    //retrieve claim from jwt token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieve any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        User user = userRepository.findByEmail(userDetails.getUsername());
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole());
        return doGenerateToken(claims);
    }

    private String doGenerateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject((String) claims.get("username"))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
