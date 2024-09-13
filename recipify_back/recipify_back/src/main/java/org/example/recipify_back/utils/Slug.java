package org.example.recipify_back.utils;

import org.example.recipify_back.repository.RecipeRepository;

import java.text.Normalizer;


public class Slug {

    private final RecipeRepository recipeRepository;

    public Slug(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // Méthode pour générer un slug unique
    public String generateUniqueSlug(String baseSlug) {
        // Normaliser la chaîne pour retirer les accents et formater le slug
        String slug = normalizeString(baseSlug);

        int count = 1;
        // Tant que le slug existe dans la base de données, ajouter un suffixe unique
        while (recipeRepository.existsBySlug(slug)) {
            slug = slug + "_" + count++;
        }

        return slug;
    }

    // Méthode pour normaliser la chaîne (retirer les accents et formater correctement le slug)
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
