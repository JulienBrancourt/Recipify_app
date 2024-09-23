package org.example.recipify_back.utils;

import org.example.recipify_back.repository.RecipeRepository;

import java.text.Normalizer;


public class Slug {

    private final RecipeRepository recipeRepository;

    public Slug(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    public String generateUniqueSlug(String baseSlug) {

        String slug = normalizeString(baseSlug);

        int count = 1;

        while (recipeRepository.existsBySlug(slug)) {
            slug = slug + "_" + count++;
        }

        return slug;
    }


    private String normalizeString(String input) {
        // 1. Normaliser en forme canonique décomposée (NFD)
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        // 2. Retirer les accents (supprimer les diacritiques)
        String noAccents = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // 3. Remplacer les caractères non alphanumériques par des underscores
        String slug = noAccents.replaceAll("[^a-zA-Z0-9]+", "_").toLowerCase();

        // 4. Supprimer les underscores en début ou fin de chaîne
        slug = slug.replaceAll("^_+|_+$", "");

        return slug;
    }
}
