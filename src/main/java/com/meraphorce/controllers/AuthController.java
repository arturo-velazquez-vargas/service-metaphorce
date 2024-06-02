package com.meraphorce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meraphorce.dtos.AuthRequest;
import com.meraphorce.dtos.RegisterRequest;
import com.meraphorce.dtos.UserResponse;
import com.meraphorce.services.AuthService;
import com.meraphorce.services.JwtService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@Validated
public class AuthController {
	
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtService jwtService;
    
    @PostMapping("/registerUser")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterRequest userInfo) {
    	log.info("Registering new user.");
        UserResponse dto = authService.registerUser(userInfo);
        return ResponseEntity.ok( dto );
    }
    
    @GetMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    	log.info("Generating token.");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
    
}
