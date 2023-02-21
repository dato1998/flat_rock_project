package com.flatRock.project.productService.configs;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class JwtGrantedAuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    public JwtGrantedAuthorityConverter() {
        //Bean extracting authority
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        return Collections.singletonList(new SimpleGrantedAuthority(source.getClaims().get("role").toString()));
    }

    @Override
    public <U> Converter<Jwt, U> andThen(Converter<? super Collection<GrantedAuthority>, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
