package org.example.recipify_back.repository;

import org.example.recipify_back.entity.Ingredient;
import org.example.recipify_back.entity.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Ingredient findByIngredientName(String ingredientName);

    List<Ingredient> findByIngredientCategory(IngredientCategory category);

}
