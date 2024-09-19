package org.example.recipify_back.service;

import lombok.extern.slf4j.Slf4j;
import org.example.recipify_back.entity.FridgeItem;
import org.example.recipify_back.entity.Ingredient;
import org.example.recipify_back.entity.User;
import org.example.recipify_back.entity.enumEntity.UnitOfMeasurement;
import org.example.recipify_back.repository.FridgeRepository;
import org.example.recipify_back.repository.IngredientRepository;
import org.example.recipify_back.security.AuthService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FridgeService {
    private final FridgeRepository fridgeRepository;
    private final IngredientRepository ingredientRepository;
    private final AuthService authService;

    public FridgeService(FridgeRepository fridgeRepository, IngredientRepository ingredientRepository, AuthService authService) {
        this.fridgeRepository = fridgeRepository;
        this.ingredientRepository = ingredientRepository;
        this.authService = authService;
    }

    public List<Map<String, Object>> getFridgeItems() {
        User user = authService.getAuthUser();
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
        return items;
    }


    public void saveFridgeItems(Object requestBody) {

        User user = authService.getAuthUser();

        // Validate and cast requestBody
        if (!(requestBody instanceof List<?> rawItems)) {
            throw new IllegalArgumentException("Invalid request body format. Expected a list of items.");
        }

        if (rawItems.isEmpty() || !(rawItems.get(0) instanceof Map)) {
            throw new IllegalArgumentException("Invalid request body format.");
        }


        List<Map<String, Object>> items = (List<Map<String, Object>>) rawItems;

        List<FridgeItem> fridgeItems = new ArrayList<>();


        for (Map<String, Object> itemData : items) {

            String name = (String) itemData.get("name");
            Integer quantity = (Integer) itemData.get("quantity");
            String unit = (String) itemData.get("unit");
            String expirationStr = (String) itemData.get("expiration");

            if (name == null || quantity == null || unit == null || expirationStr == null) {
                throw new IllegalArgumentException("Missing required fields in item data");
            }


            LocalDate expiration;
            try {
                expiration = LocalDate.parse(expirationStr);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid expiration date format for item: " + name);
            }


            Ingredient ingredient = ingredientRepository.findByIngredientName(name);
            if (ingredient == null) {
                throw new IllegalArgumentException("Ingredient not found: " + name);
            }


            FridgeItem fridgeItem = FridgeItem.builder()
                    .user(user)
                    .ingredient(ingredient)
                    .quantity(quantity)
                    .unitOfMeasurement(UnitOfMeasurement.valueOf(unit.toUpperCase())) // Handle enum case
                    .expirationDate(expiration)
                    .build();

            fridgeItems.add(fridgeItem);
        }

        fridgeRepository.saveAll(fridgeItems);

    }

}
