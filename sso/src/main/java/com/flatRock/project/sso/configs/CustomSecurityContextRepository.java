package com.flatRock.project.sso.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomSecurityContextRepository implements SecurityContextRepository {

    private CustomAuthenticationManager authenticationManager;

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        String authHeader = requestResponseHolder.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
            return new SecurityContextImpl(this.authenticationManager.authenticate(auth));
        }
        return null;
    }

    @Override
    public DeferredSecurityContext loadDeferredContext(HttpServletRequest request) {
        return SecurityContextRepository.super.loadDeferredContext(request);
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return false;
    }
}
