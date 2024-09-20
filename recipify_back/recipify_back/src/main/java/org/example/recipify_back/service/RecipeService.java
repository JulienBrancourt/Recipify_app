package org.example.recipify_back.service;

import org.example.recipify_back.entity.Ingredient;
import org.example.recipify_back.entity.Recipe;
import org.example.recipify_back.entity.RecipeIngredient;
import org.example.recipify_back.entity.User;
import org.example.recipify_back.repository.IngredientRepository;
import org.example.recipify_back.repository.RecipeRepository;
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

    public RecipeService(RecipeRepository recipeRepository, AuthService authService, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.slugGenerator = new Slug(recipeRepository);  // Instanciation du générateur de slug
        this.authService = authService;
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    public void registerRecipe(Recipe recipe) {
        List<RecipeIngredient> updatedIngredients = recipe.getRecipeIngredients().stream().map(recipeIngredient -> {
            String ingredientName = recipeIngredient.getIngredient().getIngredientName();

            log.info("Processing ingredient: " + ingredientName);

            Optional<Ingredient> ingredientFromDB = ingredientRepository.findByIngredientName(ingredientName);

            log.info(String.valueOf(ingredientFromDB.isEmpty()));

            if (ingredientFromDB.isEmpty()) {
                throw new IllegalArgumentException("Ingredient not found: " + ingredientName);
            }

            log.info("Ingredient found: " + ingredientFromDB.get());

            Ingredient ingredient = ingredientFromDB.get();

            if (!Objects.equals(ingredientName, ingredient.getIngredientName())) {
                throw new IllegalArgumentException("WTF BRO ? TA BDD EST MAL FOUTUE");
            }

            recipeIngredient.setIngredient(ingredient);

            return recipeIngredient;
        }).collect(Collectors.toList());

        recipe.setRecipeIngredients(updatedIngredients);

        // Associer chaque RecipeIngredient à la recette
        updatedIngredients.forEach(ingredient -> ingredient.setRecipe(recipe));

        log.info("Updated recipe ingredients: " + recipe.getRecipeIngredients());

        // Assigner l'utilisateur connecté comme créateur de la recette
        User user = authService.getAuthUser();
        recipe.setCreator(user);

        log.info("Recipe creator: " + user);

        recipe.setTitle(recipe.getTitle().toLowerCase());
        String baseSlug = recipe.getTitle();
        String uniqueSlug = slugGenerator.generateUniqueSlug(baseSlug);
        recipe.setSlug(uniqueSlug);
        log.info("Generated slug: " + uniqueSlug);

        recipe.setCalorie(recipe.getTotalCalories());
        log.info("Calorie count: " + recipe.getCalorie());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        recipe.setApproved(authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")));

        recipeRepository.save(recipe);

        log.info("Recipe registered: " + recipe);
    }


    public Map<String, Object> getRecipe(String slug) {
        Recipe foundRecipe = recipeRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Recipe Not Found"));
        Map<String, Object> recipe = new HashMap<>();
        recipe.put("title", foundRecipe.getTitle());
        recipe.put("slug", foundRecipe.getSlug());
        recipe.put("instruction", foundRecipe.getInstruction());
        recipe.put("calorie", foundRecipe.getCalorie());
        recipe.put("serving", foundRecipe.getServing());
        return recipe;
    }

    // TODO A revoir quand on passera par le front pour respecter les règles de RESTs
    public Recipe updateRecipe(String slug, Recipe updatedRecipe) {
        Recipe existingRecipe = recipeRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Recipe Not Found"));

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

    public List<Map<String, Object>> getAllRecipes() {
        User user = authService.getAuthUser();
        log.info("Fetching all recipes...");
        List<Recipe> recipes = recipeRepository.findAll();
        List<Map<String, Object>> recipeList = new ArrayList<>();

        for (Recipe recipe : recipes) {
            Map<String, Object> recipeData = new HashMap<>();
            recipeData.put("title", recipe.getTitle());
            recipeData.put("slug", recipe.getSlug());
            recipeData.put("instruction", recipe.getInstruction());
            recipeData.put("calorie", recipe.getCalorie());
            recipeData.put("serving", recipe.getServing());
            recipeList.add(recipeData);
        }
        return recipeList;
    }


}


//{
//        "title": "Boeuf Cuit",
//        "instruction": "cuire le boeuf",
//        "serving": 1,
//        "recipeIngredients": [{
//        "ingredientName": "boeuf",
//        "calorie": 20,
//        "ingredientCategory": "VIANDE",
//        "fridgeItems": []
//        }]
//        }
