package org.example.recipify_back.controller;

import org.example.recipify_back.entity.Allergy;
import org.example.recipify_back.entity.User;
import org.example.recipify_back.service.AllergyService;
import org.example.recipify_back.service.UserService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createAllergy(@RequestBody Allergy allergy) {
        if (allergy.getAllergyName().isEmpty()) {
            return new ResponseEntity<>("Allergy must not be empty", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(allergyService.addAllergy(allergy));
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

    @PutMapping("/allergy/{allergyName}")
    public void updateAllergy(@RequestBody Allergy allergy) throws RuntimeException {
        allergyService.updateAllergy(allergy.getAllergyName());
    }

    @DeleteMapping("/allergy/{allergyName}")
    public void deleteAllergy(@PathVariable String allergyName) throws RuntimeException {
        allergyService.deleteAllergy(allergyName);
    }
}
