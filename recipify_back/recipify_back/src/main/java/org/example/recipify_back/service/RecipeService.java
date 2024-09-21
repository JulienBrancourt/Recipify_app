package org.example.recipify_back.service;

import org.example.recipify_back.entity.Ingredient;
import org.example.recipify_back.entity.Recipe;
import org.example.recipify_back.entity.RecipeIngredient;
import org.example.recipify_back.entity.User;
import org.example.recipify_back.entity.dto.IngredientDto;
import org.example.recipify_back.entity.dto.RecipeDto;
import org.example.recipify_back.repository.IngredientRepository;
import org.example.recipify_back.repository.RecipeRepository;
import org.example.recipify_back.repository.UserRepository;
import org.example.recipify_back.security.AuthService;
import org.example.recipify_back.utils.Slug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private static final Logger log = LoggerFactory.getLogger(RecipeService.class);
    private final RecipeRepository recipeRepository;
    private final Slug slugGenerator;
    private final AuthService authService;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;


    public RecipeService(RecipeRepository recipeRepository, AuthService authService, IngredientRepository ingredientRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.slugGenerator = new Slug(recipeRepository);
        this.authService = authService;
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean registerRecipe(Recipe recipe) {
        try {
            // Traiter les ingrédients et les associer à la recette
            List<RecipeIngredient> updatedIngredients = recipe.getRecipeIngredients().stream().map(recipeIngredient -> {
                String ingredientName = recipeIngredient.getIngredient().getIngredientName();
                log.info("Processing ingredient: " + ingredientName);

                Ingredient ingredient = ingredientRepository.findByIngredientName(ingredientName)
                        .orElseThrow(() -> new IllegalArgumentException("Ingredient not found: " + ingredientName));

                recipeIngredient.setIngredient(ingredient);
                return recipeIngredient;
            }).collect(Collectors.toList());

            // Mise à jour des ingrédients et association à la recette
            recipe.setRecipeIngredients(updatedIngredients);
            updatedIngredients.forEach(ingredient -> ingredient.setRecipe(recipe));

            // Assignation de l'utilisateur comme créateur
            User user = authService.getAuthUser();
            recipe.setCreator(user);
            log.info("Recipe creator: " + user);

            // Génération du slug unique
            recipe.setTitle(recipe.getTitle().toLowerCase());
            String baseSlug = recipe.getTitle();
            String uniqueSlug = slugGenerator.generateUniqueSlug(baseSlug);
            recipe.setSlug(uniqueSlug);
            log.info("Generated slug: " + uniqueSlug);

            // Calcul des calories
            recipe.setCalorie(recipe.getTotalCalories());
            log.info("Calorie count: " + recipe.getCalorie());

            // Vérification des droits d'approbation
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            recipe.setApproved(authentication != null && authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")));

            // Enregistrement de la recette
            recipeRepository.save(recipe);
            log.info("Recipe registered: " + recipe);

            return true; // Retourne true si tout est réussi

        } catch (IllegalArgumentException e) {
            log.error("Error registering recipe: ", e);
            throw e; // Lancer l'exception pour une gestion par le contrôleur
        } catch (Exception e) {
            log.error("Unexpected error during recipe registration: ", e);
            return false; // Retourne false si une exception imprévue survient
        }
    }



    public Map<String, Object> getRecipe(String slug) {
        Recipe foundRecipe = recipeRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Recipe Not Found"));
        Map<String, Object> recipe = new HashMap<>();
        recipe.put("title", foundRecipe.getTitle());
        recipe.put("slug", foundRecipe.getSlug());
        recipe.put("instruction", foundRecipe.getInstruction());
        recipe.put("calorie", foundRecipe.getCalorie());
        recipe.put("serving", foundRecipe.getServing());


        List<Map<String, Object>> ingredients = new ArrayList<>();
        for (RecipeIngredient ingredient : foundRecipe.getRecipeIngredients()) {
            Map<String, Object> ingredientData = new HashMap<>();
            ingredientData.put("ingredientName", ingredient.getIngredient().getIngredientName());
            ingredientData.put("calorie", ingredient.getIngredient().getCalorie());
            ingredientData.put("ingredientCategory", ingredient.getIngredient().getIngredientCategory());
            ingredientData.put("quantity", ingredient.getQuantity());
            ingredientData.put("unit", ingredient.getUnit());
            ingredients.add(ingredientData);
        }
        recipe.put("recipeIngredients", ingredients);

        return recipe;
    }


    public Recipe updateRecipe(String slug, Recipe updatedRecipe) {
        try {
            Recipe existingRecipe = recipeRepository.findBySlug(slug)
                    .orElseThrow(() -> new RuntimeException("Recipe not found with slug: " + slug));

            // Mise à jour du titre
            String newTitle = updatedRecipe.getTitle();
            if (newTitle != null && !newTitle.trim().isEmpty()) {
                existingRecipe.setTitle(newTitle.trim());
                existingRecipe.setSlug(slugGenerator.generateUniqueSlug(newTitle.trim()));
            }

            // Mise à jour des instructions
            String newInstruction = updatedRecipe.getInstruction();
            if (newInstruction != null && !newInstruction.trim().isEmpty()) {
                existingRecipe.setInstruction(newInstruction.trim());
            }

            // Mise à jour des portions
            int newServing = updatedRecipe.getServing();
            if (newServing > 0) {
                existingRecipe.setServing(newServing);
            }

            // Sauvegarder la recette mise à jour dans le repository
            recipeRepository.save(existingRecipe);

            return existingRecipe;  // Retourne la recette mise à jour

        } catch (RuntimeException e) {
            log.error("Recipe not found or error during update: ", e);
            throw e;  // Lancer l'exception capturée pour gestion dans le contrôleur
        } catch (Exception e) {
            log.error("Error updating recipe: ", e);
            throw new RuntimeException("An unexpected error occurred while updating the recipe.", e);
        }
    }




    @Transactional
    public boolean deleteRecipe(String slug) {
        try {
            log.info("Attempting to delete recipe with slug: " + slug);

            // Rechercher la recette par son slug
            Optional<Recipe> recipeOptional = recipeRepository.findBySlug(slug);

            if (recipeOptional.isPresent()) {
                Recipe recipe = recipeOptional.get();
                log.info("Recipe found: " + recipe);

                // Trouver les utilisateurs qui ont cette recette en favoris
                List<User> usersWhoFavorited = userRepository.findUsersByFavoriteRecipesContaining(recipe);
                log.info("Found " + usersWhoFavorited.size() + " users who favorited this recipe.");

                // Supprimer la recette des favoris de chaque utilisateur
                for (User user : usersWhoFavorited) {
                    log.info("Removing recipe from user: " + user.getUsername());
                    user.getFavoriteRecipes().remove(recipe);
                    userRepository.save(user);
                    log.info("Removed recipe from user: " + user.getUsername());
                }

                // Refresh des entités avant la suppression pour éviter les erreurs de suppression
                recipeRepository.flush();
                userRepository.flush();

                recipeRepository.delete(recipe);
                log.info("Deleted recipe with slug: " + slug);

                return true;
            } else {
                log.error("No recipe found with slug: " + slug);
                return false;
            }
        } catch (Exception e) {
            log.error("An error occurred while deleting the recipe: ", e);
            return false;
        }
    }


    public List<RecipeDto> getAllRecipes() {
        log.info("Fetching all recipes...");
        List<Recipe> recipes = recipeRepository.findAll();

        return recipes.stream().map(recipe -> {
            // Transformer les ingrédients en IngredientDto
            List<IngredientDto> ingredients = recipe.getRecipeIngredients().stream()
                    .map(ingredient -> new IngredientDto(
                            ingredient.getIngredient().getIngredientName(),
                            ingredient.getIngredient().getCalorie(),
                            ingredient.getIngredient().getIngredientCategory().name()))
                    .collect(Collectors.toList());

            // Transformer la recette en RecipeDto
            return new RecipeDto(
                    recipe.getTitle(),
                    recipe.getSlug(),
                    recipe.getInstruction(),
                    recipe.getCalorie(),
                    recipe.getServing(),
                    ingredients
            );
        }).collect(Collectors.toList());
    }

}
