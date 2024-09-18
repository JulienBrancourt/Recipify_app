package org.example.recipify_back.service;

import org.example.recipify_back.entity.FridgeItem;
import org.example.recipify_back.entity.Ingredient;
import org.example.recipify_back.entity.User;
import org.example.recipify_back.entity.enumEntity.UnitOfMeasurement;
import org.example.recipify_back.repository.FridgeRepository;
import org.example.recipify_back.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FridgeService {
    private final FridgeRepository fridgeRepository;
    private final IngredientRepository ingredientRepository;

    public FridgeService(FridgeRepository fridgeRepository,IngredientRepository ingredientRepository) {
        this.fridgeRepository = fridgeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public FridgeItem getFridgeByUserId(int userId) {
        return fridgeRepository.findByUserId(userId);
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
