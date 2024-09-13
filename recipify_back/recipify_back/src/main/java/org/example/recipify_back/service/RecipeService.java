package org.example.recipify_back.service;

import org.example.recipify_back.entity.Recipe;
import org.example.recipify_back.repository.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

   public Recipe registerRecipe(String title, String instruction, int serving) {
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setInstruction(instruction);
        recipe.setServing(serving);
        recipe.setCalorie(0);
        return recipeRepository.save(recipe);
   }

   public  Recipe getRecipe(String recipeName){
        return recipeRepository.findByTitle(recipeName);
   }

    public Recipe updateRecipe(String title, String instruction, int serving) {
        Recipe recipe = recipeRepository.findByTitle(title);
        if (recipe != null) {
            recipe.setTitle(title);
            recipe.setInstruction(instruction);
            recipe.setServing(serving);
            return recipeRepository.save(recipe);
        } else {
            throw new RuntimeException("Recipe not found");
        }
    }



   public void deleteRecipe(String recipeName) {
        Recipe recipe = recipeRepository.findByTitle(recipeName);
    try {
        recipeRepository.delete(recipe);
    } catch (Exception e) {
        throw new RuntimeException("Recipe not found");
    }
   }

}
