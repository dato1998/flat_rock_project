package com.flatRock.project.productService.interceptor;

import com.flatRock.project.productService.exceptions.BadCredentialsException;
import com.flatRock.project.productService.feign.SingleSignOnServiceProxy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
@Order(2)
public class RequestValidationInterceptor implements HandlerInterceptor {
    private final SingleSignOnServiceProxy sessionService;

    public RequestValidationInterceptor(@Lazy SingleSignOnServiceProxy sessionService) {
        this.sessionService = sessionService;
    }

    private void validateSession(String token) {
        sessionService.updateSession(token);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = null;
        if (request.getHeader("authorization") != null) {
            token = request.getHeader("authorization").substring(7);
            validateSession(token);
        }
        if (token == null) {
            throw new BadCredentialsException("for this request you need token, set it and try again");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
