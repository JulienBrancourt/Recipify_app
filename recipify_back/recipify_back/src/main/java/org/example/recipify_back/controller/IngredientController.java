package org.example.recipify_back.controller;

import org.example.recipify_back.entity.Ingredient;
import org.example.recipify_back.service.IngredientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
