package com.example.multiplex.model.util;

import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationResponse {

    private final String jwt;
    private final UserDetails userDetails;

    public AuthenticationResponse(String jwt, UserDetails userDetails) {
        this.jwt = jwt;
        this.userDetails = userDetails;
    }

    // ------------- GETTERS AND SETTERS -------------
    public String getJwt() { return jwt; }
    public UserDetails getUserDetails() { return userDetails; }

}
