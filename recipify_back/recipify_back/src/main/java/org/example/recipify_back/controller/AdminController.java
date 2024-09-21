package org.example.recipify_back.controller;

import org.example.recipify_back.entity.Recipe;
import org.example.recipify_back.entity.dto.RecipeDto;
import org.example.recipify_back.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    private final RecipeService recipeService;

    public AdminController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/admin")
    public List<RecipeDto> getAllRecipes(){
        return recipeService.getAllRecipes();
    }

    @PutMapping("/admin/{slug}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("slug") String slug, @RequestBody Recipe updatedRecipe){
        try {
            Recipe updated = recipeService.updateRecipe(slug, updatedRecipe);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/admin/{slug}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String slug) {
        try {
            recipeService.deleteRecipe(slug);
            return ResponseEntity.ok("Recipe deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the recipe.");
        }
    }


}
