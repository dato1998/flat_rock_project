package com.flatRock.project.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator router(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(r -> r.path("/sso/**")
                        .uri("lb://single-sign-on"))
                .route(r -> r.path("/session/**")
                        .uri("lb://single-sign-on"))
                .route(r -> r.path("/notification/**")
                        .uri("lb://notification-service"))
                .route(r -> r.path("/clients/**")
                        .uri("lb://product-service"))
                .route(r -> r.path("/orders/**")
                        .uri("lb://product-service"))
                .route(r -> r.path("/products/**")
                        .uri("lb://product-service"))
                .build();
    }
}
