package org.example.recipify_back.service;

import org.example.recipify_back.entity.Ingredient;
import org.example.recipify_back.entity.IngredientCategory;
import org.example.recipify_back.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient addIngredient(Ingredient ingredient) {

        ingredient.setIngredientName(ingredient.getIngredientName().toLowerCase());
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientByName(String name) {
        return ingredientRepository.findByIngredientName(name);
    }

    public List<Ingredient> getIngredientsByCategory(IngredientCategory category) {
        return ingredientRepository.findByIngredientCategory(category);
    }
}
