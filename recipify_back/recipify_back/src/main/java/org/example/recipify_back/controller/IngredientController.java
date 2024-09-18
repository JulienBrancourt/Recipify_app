package org.example.recipify_back.controller;

import org.example.recipify_back.entity.Ingredient;
import org.example.recipify_back.entity.enumEntity.IngredientCategory;
import org.example.recipify_back.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientController {
    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/ingredient")
    public ResponseEntity<?> addIngredient(@RequestBody Ingredient ingredient) {
        try {
            ingredientService.addIngredient(ingredient);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("ingredient created");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/ingredient/{ingredientName}")
    public Ingredient getIngredient(@PathVariable String ingredientName) {
        return ingredientService.getIngredientByName(ingredientName);
    }

    @GetMapping("/ingredients")
    public List<Ingredient> getAllIngredients() {
        // Vous pouvez ajouter une logique en fonction des informations récupérées
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/ingredient")
    public List<Ingredient> getIngredientsByCategory(@RequestParam String category) {
        IngredientCategory ingredientCategory;
        try {
            ingredientCategory = IngredientCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ingredient category");
        }
        return ingredientService.getIngredientsByCategory(ingredientCategory);
    }
}
