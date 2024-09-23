package org.example.recipify_back.controller;

import org.example.recipify_back.entity.User;
import org.example.recipify_back.entity.dto.UserRegistrationDto;
import org.example.recipify_back.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class registerController {

    private static final Logger log = LoggerFactory.getLogger(registerController.class);
    private final UserService userService;

    public registerController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationDto registrationDto) {
        log.info("Registering user: {}", registrationDto.getUsername());

        List<String> allergies = registrationDto.getAllergies();
        List<String> diets = registrationDto.getDiets();

        log.info("Allergies: {}", allergies);
        log.info("Diets: {}", diets);


        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(registrationDto.getPassword());


        User registeredUser = userService.registerUser(user, allergies, diets);

        return ResponseEntity.ok(registeredUser);
    }
}
