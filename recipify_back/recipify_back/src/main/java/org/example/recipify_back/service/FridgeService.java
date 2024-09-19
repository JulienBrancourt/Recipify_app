package org.example.recipify_back.service;

import lombok.extern.slf4j.Slf4j;
import org.example.recipify_back.entity.FridgeItem;
import org.example.recipify_back.entity.Ingredient;
import org.example.recipify_back.entity.User;
import org.example.recipify_back.entity.enumEntity.UnitOfMeasurement;
import org.example.recipify_back.repository.FridgeRepository;
import org.example.recipify_back.repository.IngredientRepository;
import org.example.recipify_back.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FridgeService {
    private final FridgeRepository fridgeRepository;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;

    public FridgeService(FridgeRepository fridgeRepository, IngredientRepository ingredientRepository, UserRepository userRepository) {
        this.fridgeRepository = fridgeRepository;
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
    }

    public List<Map<String, Object>> getFridgeItems() {
        // Récupération de l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalArgumentException("User not found");
        }

        String userName = authentication.getName();

        if (userName == null) {
            throw new RuntimeException("User not authenticated");
        }

        // Recherche de l'utilisateur dans la base de données
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        log.info("User found: {}", user.getUsername());


        List<FridgeItem> fridgeItems = fridgeRepository.findByUser(user);

        List<Map<String, Object>> items = new ArrayList<>();

        for (FridgeItem fridgeItem : fridgeItems) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", fridgeItem.getIngredient().getIngredientName());
            item.put("quantity", fridgeItem.getQuantity());
            item.put("unit", fridgeItem.getUnitOfMeasurement().name());
            item.put("expiration", fridgeItem.getExpirationDate().toString());
            items.add(item);
        }

        // Retourner la liste des éléments du frigo sous forme de Map
        return items;
    }


    public void saveFridgeItems(Object requestBody, User user) {
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<Map<String, Object>> items = (List<Map<String, Object>>) requestBody;

        List<FridgeItem> fridgeItems = new ArrayList<>();

        for (Map<String, Object> itemData : items) {
            String name = (String) itemData.get("name");
            int quantity = (Integer) itemData.get("quantity");
            String unit = (String) itemData.get("unit");
            LocalDate expiration = LocalDate.parse((String) itemData.get("expiration"));

            Ingredient ingredient = ingredientRepository.findByIngredientName(name);
            if (ingredient == null) {
                throw new IllegalArgumentException("Ingredient not found: " + name);
            }


            FridgeItem fridgeItem = FridgeItem.builder()
                    .user(user)
                    .ingredient(ingredient)
                    .quantity(quantity)
                    .unitOfMeasurement(UnitOfMeasurement.valueOf(unit.toUpperCase())) // Ensure correct enum
                    .expirationDate(expiration)
                    .build();

            fridgeItems.add(fridgeItem);
        }
        System.out.println("Fridge items to save: " + fridgeItems);
        fridgeRepository.saveAll(fridgeItems);
        System.out.println("Fridge items saved for user: " + user.getId());
    }
}
