package org.example.recipify_back.controller;

import org.example.recipify_back.entity.User;
import org.example.recipify_back.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public User register(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam(defaultValue = "false") boolean isAdmin) {
        return userService.registerUser(username, password, isAdmin);
    }
}
