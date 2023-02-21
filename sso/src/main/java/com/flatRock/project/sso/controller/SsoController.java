package com.flatRock.project.sso.controller;

import com.flatRock.project.sso.model.JwtRequest;
import com.flatRock.project.sso.model.UserInformation;
import com.flatRock.project.sso.service.SsoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/sso")
public class SsoController {
    private final SsoService userDetailsService;

    public SsoController(SsoService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserInformation> createLoginToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        return new ResponseEntity<>(userDetailsService.login(authenticationRequest), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<UserInformation> isTokenActive(final String token) {
        return new ResponseEntity<>(userDetailsService.isTokenActive(token), HttpStatus.OK);
    }

    @GetMapping("/token")
    public ResponseEntity<String> generateToken(@RequestParam final String email) {
        return new ResponseEntity<>(userDetailsService.generateToken(email), HttpStatus.OK);
    }

    @GetMapping("/user/info")
    public ResponseEntity<Map<String, Object>> getUserIdAndRole(@RequestParam final String token) {
        return new ResponseEntity<>(userDetailsService.getUserIdAndRole(token), HttpStatus.OK);
    }
}