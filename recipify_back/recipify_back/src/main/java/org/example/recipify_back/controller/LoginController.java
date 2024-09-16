package org.example.recipify_back.controller;

import org.example.recipify_back.entity.User;
import org.example.recipify_back.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public User register(@RequestBody User user) {
        log.info("Registering user: {}", user);
        return userService.registerUser(user);
    }
}
