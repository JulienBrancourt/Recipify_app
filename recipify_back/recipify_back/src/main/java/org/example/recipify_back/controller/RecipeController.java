package org.example.recipify_back.controller;

import org.example.recipify_back.entity.Recipe;
import org.example.recipify_back.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipe")
    public void registerRecipe(@RequestBody Recipe recipe) {
        recipeService.registerRecipe(recipe.getTitle(), recipe.getInstruction(), recipe.getServing());
    }

    @GetMapping("/recipe/{recipeName}")
    public Recipe getRecipe(@PathVariable String recipeName) {
        return recipeService.getRecipe(recipeName);
    }

    @PutMapping("/recipe/{recipeName}")
    public void updateRecipe(@RequestBody Recipe recipe) {
        recipeService.updateRecipe(recipe.getTitle(), recipe.getInstruction(), recipe.getServing());
    }



    @DeleteMapping("/recipe/{recipeName}")
    public void deleteRecipe(@PathVariable String recipeName) {
        recipeService.deleteRecipe(recipeName);
    }
}
