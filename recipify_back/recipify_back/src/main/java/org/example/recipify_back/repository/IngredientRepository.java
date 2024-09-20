package org.example.recipify_back.repository;

import org.example.recipify_back.entity.Ingredient;
import org.example.recipify_back.entity.enumEntity.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Optional<Ingredient> findByIngredientName(String ingredientName);
    List<Ingredient> findByIngredientCategory(IngredientCategory category);
}