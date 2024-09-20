package org.example.recipify_back.controller;

import org.example.recipify_back.entity.Recipe;
import org.example.recipify_back.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminController {
    private final RecipeService recipeService;

    public AdminController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/admin")
    public List<Map<String, Object>> getAllRecipes(){
        return recipeService.getAllRecipes();
    }

    @PutMapping("/admin/{slug}")
    public ResponseEntity<?>updateRecipe(@PathVariable("slug") String slug, @RequestBody Recipe updatedRecipe){
        try {
            recipeService.updateRecipe(slug, updatedRecipe);
            return ResponseEntity.ok("Recipe updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while updating the recipe");
        }
    }

    @DeleteMapping("/admin/{slug}")
    public ResponseEntity<?> deleteRecipe(@PathVariable String slug) {
        try {
            recipeService.deleteRecipe(slug);
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
