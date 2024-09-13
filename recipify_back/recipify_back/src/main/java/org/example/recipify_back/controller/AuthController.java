package org.example.recipify_back.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.recipify_back.entity.AuthRequest;
import org.example.recipify_back.security.JwtUtil;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil1) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil1;
    }

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            log.info("Attempting to authenticate user: {}", authRequest.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            log.info("Authentication successful for user: {}", authRequest.getUsername());
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", authRequest.getUsername(), e);
            throw new Exception("Incorrect username or password", e);
        }

        // Générer un token JWT et le retourner
        String jwt = jwtUtil.generateToken(authRequest.getUsername());
        log.info("JWT generated for user: {}", authRequest.getUsername());
        return jwt;
    }
}
