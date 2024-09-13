package org.example.recipify_back.repository;

import org.example.recipify_back.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Recipe findByTitle(String title);

    Recipe findBySlug(String slug);

    boolean existsBySlug(String slug);
}
