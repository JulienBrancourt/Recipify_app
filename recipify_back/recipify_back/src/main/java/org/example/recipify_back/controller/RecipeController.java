package org.example.recipify_back.controller;

import org.example.recipify_back.entity.Recipe;
import org.example.recipify_back.entity.User;
import org.example.recipify_back.service.RecipeService;
import org.example.recipify_back.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;

    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @PostMapping("/recipe")
    public void registerRecipe(@RequestBody Recipe recipe) {
        recipeService.registerRecipe(recipe);
    }

    @PostMapping("/userFavoriteRecipe")
    public ResponseEntity<User> updateUserFavoriteRecipe(@RequestParam String userName,
             @RequestParam String slug) {
        User updateUserFavorite = userService.addFavoriteRecipeToUser(userName, slug);
        return ResponseEntity.ok(updateUserFavorite);
    }

    @GetMapping("/recipe/{slug}")
    public Recipe getRecipe(@PathVariable("slug") String slug) {
        return recipeService.getRecipe(slug);
    }

    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @PutMapping("/recipe/{slug}")
    public void updateRecipe(@PathVariable("slug") String slug, @RequestBody Recipe updatedRecipe) {
        recipeService.updateRecipe(slug, updatedRecipe);
    }

    @DeleteMapping("/recipe/{recipeName}")
    public void deleteRecipe(@PathVariable String recipeName) {
        recipeService.deleteRecipe(recipeName);
    }


}
