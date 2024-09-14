package org.example.recipify_back.controller;

import org.example.recipify_back.entity.Ingredient;
import org.example.recipify_back.entity.IngredientCategory;
import org.example.recipify_back.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class IngredientController {
    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/ingredient")
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(ingredient);
    }

    @GetMapping("/ingredient/{ingredientName}")
    public Ingredient getIngredient(@PathVariable String ingredientName) {
        return ingredientService.getIngredientByName(ingredientName);
    }

    @GetMapping("/ingredients")
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/ingredient")
    public List<Ingredient> getIngredientsByCategory(@RequestParam String category) {
        IngredientCategory ingredientCategory;
        try {
            // Conversion de la chaîne en enum
            ingredientCategory = IngredientCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ingredient category");
        }

        return ingredientService.getIngredientsByCategory(ingredientCategory);
    }
}
