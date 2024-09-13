package org.example.recipify_back.controller;

import org.example.recipify_back.entity.User;
import org.example.recipify_back.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }
}
