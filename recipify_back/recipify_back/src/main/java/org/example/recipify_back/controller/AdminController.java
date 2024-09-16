package org.example.recipify_back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public Map<String, String> admin() {
    Map<String, String> response = new HashMap<>();
        response.put("message","Welcome Admin!");
        return response;
    }
}
