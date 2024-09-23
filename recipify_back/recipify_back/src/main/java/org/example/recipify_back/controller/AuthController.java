package org.example.recipify_back.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.recipify_back.entity.security.AuthRequest;
import org.example.recipify_back.security.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        log.info("Received authentication request for user: {}", authRequest.getUsername());

        try {
            log.info("Attempting to authenticate user: {}", authRequest.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            log.info("Authentication successful for user: {}", authRequest.getUsername());

        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", authRequest.getUsername(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Incorrect username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }


        String jwt = jwtUtil.generateToken(authRequest.getUsername());


        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);

        log.info("JWT generated for user: {}", authRequest.getUsername());
        return ResponseEntity.ok(response);
    }

}
