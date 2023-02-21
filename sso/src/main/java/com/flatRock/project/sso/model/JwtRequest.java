package com.flatRock.project.sso.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {
    private String email;
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
