package org.example.recipify_back.controller;

import org.example.recipify_back.entity.Allergy;
import org.example.recipify_back.entity.User;
import org.example.recipify_back.service.AllergyService;
import org.example.recipify_back.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AllergyController {
    private AllergyService allergyService;
    private UserService userService;

    public AllergyController(AllergyService allergyService, UserService userService) {
        this.allergyService = allergyService;
        this.userService = userService;
    }

    @PostMapping("/allergy")
    public Allergy createAllergy(@RequestBody Allergy allergy) {
        return allergyService.addAllergy(allergy);
    }

    @PostMapping("/userAllergy")
    public ResponseEntity<User> updateAllergy(
            @RequestParam String userName,
            @RequestParam String allergyName
    ) {
        User updatedUser = userService.addAllergyToUser(userName, allergyName);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/allergies")
    public List<Allergy> getAllAllergies() {
        return allergyService.getAllAllergy();
    }
}
