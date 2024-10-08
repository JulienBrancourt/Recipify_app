package org.example.recipify_back.repository;

import org.example.recipify_back.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Recipe findByTitle(String title);

    Optional<Recipe> findBySlug(String slug);

    boolean existsBySlug(String slug);

    Recipe findBySlugAndIsApproved(String slug, boolean isApproved);


}
