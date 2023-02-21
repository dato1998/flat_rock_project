package com.flatRock.project.productService.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request ->
                        request
                                //we can also have some access control in config class like this
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers("/orders/**").hasAnyAuthority("CLIENT", "SELLER", "ADMINISTRATOR")
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauthResourceServer -> oauthResourceServer.jwt().jwtAuthenticationConverter(authenticationConverter()))
                .build();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> authenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new JwtGrantedAuthorityConverter());
        return converter;
    }
}
