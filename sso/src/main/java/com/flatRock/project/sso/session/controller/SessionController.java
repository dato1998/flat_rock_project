package com.flatRock.project.sso.session.controller;

import com.flatRock.project.sso.session.service.SessionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/session")
public class SessionController {
    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @DeleteMapping("/logout")
    public ResponseEntity deleteUnit(@RequestHeader HttpHeaders headers) {
        service.deleteSessionUnit(headers);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUserId(@RequestHeader HttpHeaders headers) {
        return new ResponseEntity<>(service.getUserId(headers), HttpStatus.OK);
    }

    @GetMapping("/user/information")
    public ResponseEntity<Map<String, String>> getUserInformation(@RequestHeader HttpHeaders headers) {
        return new ResponseEntity<>(service.getUserInformation(headers), HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<Map<String, Object>> getEmail(@RequestParam final String token) {
        return new ResponseEntity<>(service.getEmail(token), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity updateSession(@RequestParam String token) {
        service.updateSession(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
