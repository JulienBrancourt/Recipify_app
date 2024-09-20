package org.example.recipify_back.service;

import org.example.recipify_back.entity.Allergy;
import org.example.recipify_back.entity.Diet;
import org.example.recipify_back.entity.Recipe;
import org.example.recipify_back.entity.User;
import org.example.recipify_back.repository.AllergyRepository;
import org.example.recipify_back.repository.DietRepository;
import org.example.recipify_back.repository.RecipeRepository;
import org.example.recipify_back.repository.UserRepository;
import org.example.recipify_back.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AllergyRepository allergyRepository;
    private final RecipeRepository recipeRepository;
    private final DietRepository dietRepository;
    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AllergyRepository allergyRepository, RecipeRepository recipeRepository, DietRepository dietRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.allergyRepository = allergyRepository;
        this.recipeRepository = recipeRepository;
        this.dietRepository = dietRepository;
        this.authService = authService;
    }

    public User registerUser(User user, List<String> allergyNames, List<String> dietNames) {
        // Check if the username already exists
        if (userRepository.findByUsername(user.getUsername().toLowerCase()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Fetch the allergy entities by their names
        List<Allergy> allergies = allergyRepository.findByAllergyNameIn(allergyNames);
        user.setAllergies(allergies);

        // Log les allergies trouvées
        log.info("Allergies trouvées pour {}: {}", user.getUsername(), allergies.stream()
                .map(Allergy::getAllergyName)
                .collect(Collectors.joining(", ")));

        // Fetch the diet entities by their names
        List<Diet> diets = dietRepository.findByDietNameIn(dietNames);
        user.setDiets(diets);

        // Log les régimes trouvés
        log.info("Régimes trouvés pour {}: {}", user.getUsername(), diets.stream()
                .map(Diet::getDietName)
                .collect(Collectors.joining(", ")));

        // Save the user
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User addAllergyToUser(String userName, String allergyName) {
        User user = userRepository.findByUsername(userName.toLowerCase())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Allergy allergy = allergyRepository.findByAllergyName(allergyName.toLowerCase())
                .orElseThrow(() -> new RuntimeException("Allergy not found"));

        user.getAllergies().add(allergy);
        return userRepository.save(user);
    }

    public User addFavoriteRecipeToUser(String slug) {
        log.info("slug: " + slug);
        Recipe recipe = recipeRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("No slug Found"));
        User user = authService.getAuthUser();
        user.getFavoriteRecipes().add(recipe);
        return userRepository.save(user);
    }


    public List<String> getUserFavoriteRecipes() {
        List<String> favoriteRecipeTitles = new ArrayList<>();
        User user = authService.getAuthUser();
        user.getFavoriteRecipes().forEach(recipes -> favoriteRecipeTitles.add(recipes.getTitle()));
        return favoriteRecipeTitles;
    }
}
