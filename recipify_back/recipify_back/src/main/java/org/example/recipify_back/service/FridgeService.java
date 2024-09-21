package org.example.recipify_back.service;

import lombok.extern.slf4j.Slf4j;
import org.example.recipify_back.entity.FridgeItem;
import org.example.recipify_back.entity.Ingredient;
import org.example.recipify_back.entity.User;
import org.example.recipify_back.entity.dto.FridgeDto;
import org.example.recipify_back.entity.enumEntity.UnitOfMeasurement;
import org.example.recipify_back.repository.FridgeRepository;

import org.example.recipify_back.repository.IngredientRepository;
import org.example.recipify_back.security.AuthService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

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

    public List<FridgeDto> getFridgeItems() {
        User user = authService.getAuthUser();
        List<FridgeItem> fridgeItems = fridgeRepository.findByUser(user);

        List<FridgeDto> items = new ArrayList<>();

        for (FridgeItem fridgeItem : fridgeItems) {
            FridgeDto itemDto = new FridgeDto(
                    fridgeItem.getIngredient().getIngredientName(),
                    fridgeItem.getQuantity(),
                    fridgeItem.getUnitOfMeasurement().name(),
                    fridgeItem.getExpirationDate()
            );
            items.add(itemDto);
        }
        return items;
    }


    public boolean saveFridgeItems(Object requestBody) {
        try {
            User user = authService.getAuthUser();

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
                    log.info("Expiration date: " + expiration);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid expiration date format for item: " + name);
                }

                Optional<Ingredient> ingredientOptional = ingredientRepository.findByIngredientName(name);
                if (ingredientOptional.isEmpty()) {
                    throw new IllegalArgumentException("Ingredient not found: " + name);
                }

                Ingredient ingredient = ingredientOptional.get();
                FridgeItem fridgeItem = FridgeItem.builder()
                        .user(user)
                        .ingredient(ingredient)
                        .quantity(quantity)
                        .unitOfMeasurement(UnitOfMeasurement.valueOf(unit.toUpperCase())) // Handle enum case
                        .expirationDate(expiration)
                        .build();

                fridgeItems.add(fridgeItem);
            }

            fridgeRepository.saveAll(fridgeItems);  // Sauvegarder les items dans le repository
            return true;  // Retourner true si tout s'est bien passé

        } catch (IllegalArgumentException | DateTimeParseException e) {
            log.error("Error while saving fridge items: ", e);
            return false;  // Retourner false en cas d'erreur
        }
    }
}
