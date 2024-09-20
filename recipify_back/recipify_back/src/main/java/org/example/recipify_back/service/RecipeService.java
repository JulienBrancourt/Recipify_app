package org.example.recipify_back.service;

import org.example.recipify_back.entity.Recipe;
import org.example.recipify_back.repository.RecipeRepository;
import org.example.recipify_back.utils.Slug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
public class RecipeService {
    private static final Logger log = LoggerFactory.getLogger(RecipeService.class);
    private final RecipeRepository recipeRepository;
    private final Slug slugGenerator;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
        this.slugGenerator = new Slug(recipeRepository);
        this.authService = authService;
        this.ingredientRepository = ingredientRepository;
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


    public Map<String, Object> getRecipe(String slug) {
        Recipe foundRecipe = recipeRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Recipe Not Found"));
        Map<String, Object> recipe = new HashMap<>();
        recipe.put("title", foundRecipe.getTitle());
        recipe.put("slug", foundRecipe.getSlug());
        recipe.put("instruction", foundRecipe.getInstruction());
        recipe.put("calorie", foundRecipe.getCalorie());
        recipe.put("serving", foundRecipe.getServing());

        // Add recipe ingredients
        List<Map<String, Object>> ingredients = new ArrayList<>();
        for (RecipeIngredient ingredient : foundRecipe.getRecipeIngredients()) {
            Map<String, Object> ingredientData = new HashMap<>();
            ingredientData.put("ingredientName", ingredient.getIngredient().getIngredientName());
            ingredientData.put("calorie", ingredient.getIngredient().getCalorie());
            ingredientData.put("ingredientCategory", ingredient.getIngredient().getIngredientCategory());
            ingredients.add(ingredientData);
        }
        recipe.put("recipeIngredients", ingredients);

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

            // Add recipe ingredients
            List<Map<String, Object>> ingredients = new ArrayList<>();
            for (RecipeIngredient ingredient : recipe.getRecipeIngredients()) {
                Map<String, Object> ingredientData = new HashMap<>();
                ingredientData.put("ingredientName", ingredient.getIngredient().getIngredientName());
                ingredientData.put("calorie", ingredient.getIngredient().getCalorie());
                ingredientData.put("ingredientCategory", ingredient.getIngredient().getIngredientCategory());
                ingredients.add(ingredientData);
            }
            recipeData.put("recipeIngredients", ingredients);

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
