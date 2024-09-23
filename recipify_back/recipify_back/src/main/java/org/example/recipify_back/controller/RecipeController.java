package org.example.recipify_back.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.recipify_back.entity.Recipe;
import org.example.recipify_back.entity.dto.AddFavoriteRecipeDto;
import org.example.recipify_back.entity.dto.FavoriteRecipeDetailsDto;
import org.example.recipify_back.entity.dto.RecipeDto;
import org.example.recipify_back.service.RecipeService;
import org.example.recipify_back.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;

    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @PostMapping("/recipe")
    public ResponseEntity<Map<String, String>> registerRecipe(@RequestBody Recipe recipe) {
        Map<String, String> response = new HashMap<>();
        try {
            if (recipeService.registerRecipe(recipe)) {
                response.put("message", "Recette ajoutée avec succès");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("message", "Une erreur est survenue lors de l'enregistrement de la recette.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException e) {
            response.put("message", "Recette non trouvée ou autre erreur spécifique.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }



    @PostMapping("/userFavoriteRecipe")
    public ResponseEntity<String> addFavoriteRecipe(@RequestBody AddFavoriteRecipeDto requestBody) {
        try {
            if (userService.addFavoriteRecipeToUser(requestBody.getSlug())) {
                log.info("Favorite recipe added successfully.");
                return ResponseEntity.status(HttpStatus.CREATED).body("Favorite recipe added successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to add favorite recipe.");
            }
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
    public ResponseEntity<List<FavoriteRecipeDetailsDto>> getUserFavoriteRecipes() {
        List<FavoriteRecipeDetailsDto> favoriteRecipes = userService.getUserFavoriteRecipes();
        return ResponseEntity.ok(favoriteRecipes);
    }


    @GetMapping("/recipe/{slug}")
    public Map<String, Object> getRecipe(@PathVariable("slug") String slug) {
        System.out.println("slug: " + slug);
        return recipeService.getRecipe(slug);
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }


    @PutMapping("/recipe/{slug}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("slug") String slug, @RequestBody Recipe updatedRecipe) {
        try {
            Recipe updated = recipeService.updateRecipe(slug, updatedRecipe);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/recipe/{slug}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String slug) {
        if (recipeService.deleteRecipe(slug)) {
            return ResponseEntity.status(HttpStatus.OK).body("Recipe deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found or an error occurred during deletion.");
        }
    }
}
