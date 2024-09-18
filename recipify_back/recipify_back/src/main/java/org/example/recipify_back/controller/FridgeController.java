package org.example.recipify_back.controller;

import org.example.recipify_back.entity.User;
import org.example.recipify_back.security.JwtUtil;
import org.example.recipify_back.service.FridgeService;
import org.example.recipify_back.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FridgeController {

    private final FridgeService fridgeService;
    private final UserService userService;
    private final JwtUtil jwtUtil ;
    private static final Logger logger = LoggerFactory.getLogger(FridgeController.class);

    public FridgeController(FridgeService fridgeService, UserService userService, JwtUtil jwtUtil) {
        this.fridgeService = fridgeService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/fridge")
    public ResponseEntity<?> addIngredientsToFridge(@RequestBody Object requestBody, @RequestHeader("Authorization") String token) {

        String username = jwtUtil.extractUsername(token.substring(7));

        User user = userService.findByUsername(username);
        logger.info("User found: {}", user.getUsername());
        fridgeService.saveFridgeItems(requestBody, user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Ingrédients ajoutés avec succès");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
