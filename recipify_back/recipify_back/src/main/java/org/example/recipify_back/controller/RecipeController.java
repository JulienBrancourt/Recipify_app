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
    public ResponseEntity<?> registerRecipe(@RequestBody Recipe recipe) {
        try {
            recipeService.registerRecipe(recipe);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Recipe created");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while registering the recipe.");
        }
    }

    @PostMapping("/userFavoriteRecipe")
    public ResponseEntity<?> updateUserFavoriteRecipe(
                                                      @RequestParam String slug) {
        try {
            User updateUserFavorite = userService.addFavoriteRecipeToUser(slug);
            return ResponseEntity.ok(updateUserFavorite);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating user favorite recipes.");
        }
    }

    @GetMapping("/favorite")
    public List<String> getUserFavoriteRecipes() {
        return userService.getUserFavoriteRecipes();
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
    public ResponseEntity<?> updateRecipe(@PathVariable("slug") String slug, @RequestBody Recipe updatedRecipe) {
        try {
            recipeService.updateRecipe(slug, updatedRecipe);
            return ResponseEntity.ok("Recipe updated successfully.");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Recipe not found.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the recipe.");
        }
    }

    @DeleteMapping("/recipe/{recipeName}")
    public ResponseEntity<?> deleteRecipe(@PathVariable String recipeName) {
        try {
            recipeService.deleteRecipe(recipeName);
            return ResponseEntity.ok("Recipe deleted successfully.");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Recipe not found.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the recipe.");
        }
    }

}
