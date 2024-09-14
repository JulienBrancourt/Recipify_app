package org.example.recipify_back.service;

import org.example.recipify_back.entity.Recipe;
import org.example.recipify_back.repository.RecipeRepository;
import org.example.recipify_back.utils.Slug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Service
public class RecipeService {
    private static final Logger log = LoggerFactory.getLogger(RecipeService.class);
    private final RecipeRepository recipeRepository;
    private final Slug slugGenerator;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
        this.slugGenerator = new Slug(recipeRepository);  // Instanciation du générateur de slug
    }

    public Recipe registerRecipe(Recipe recipe) {
        recipe.setTitle(recipe.getTitle().toLowerCase());

        // Générer un slug unique avec accents retirés et formatage propre
        String baseSlug = recipe.getTitle();
        String uniqueSlug = slugGenerator.generateUniqueSlug(baseSlug);
        recipe.setSlug(uniqueSlug);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            log.info("User is admin, approving recipe automatically");
            recipe.setApproved(true);
        } else {
            log.info("User is not admin, recipe will be pending approval");
            recipe.setApproved(false);
        }

        return recipeRepository.save(recipe);
    }

    public Recipe getRecipe(String slug) {
        return recipeRepository.findBySlug(slug);
    }

    // TODO A revoir quand on passera par le front pour respecter les règles de RESTs
    public Recipe updateRecipe(String slug, Recipe updatedRecipe) {
        Recipe existingRecipe = recipeRepository.findBySlug(slug);

        if (existingRecipe == null) {
            throw new RuntimeException("Recipe not found with slug: " + slug);
        }

        String newTitle = updatedRecipe.getTitle();
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            existingRecipe.setTitle(newTitle.trim());
            existingRecipe.setSlug(slugGenerator.generateUniqueSlug(newTitle.trim()));
        }

        String newInstruction = updatedRecipe.getInstruction();
        if (newInstruction != null && !newInstruction.trim().isEmpty()) {
            existingRecipe.setInstruction(newInstruction.trim());
        }

        int newServing = updatedRecipe.getServing();
        if (newServing > 0) {
            existingRecipe.setServing(newServing);
        }

        // Sauvegarde de la recette mise à jour
        return recipeRepository.save(existingRecipe);
    }


    public void deleteRecipe(String recipeName) {
        Recipe recipe = recipeRepository.findByTitle(recipeName);
        try {
            recipeRepository.delete(recipe);
        } catch (Exception e) {
            throw new RuntimeException("Recipe not found");
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
}
