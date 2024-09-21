package org.example.recipify_back.controller;

import org.example.recipify_back.entity.dto.FridgeDto;
import org.example.recipify_back.service.FridgeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FridgeController {

    private final FridgeService fridgeService;

    private static final Logger logger = LoggerFactory.getLogger(FridgeController.class);

    public FridgeController(FridgeService fridgeService) {
        this.fridgeService = fridgeService;
    }

    @PostMapping("/fridge")
    public ResponseEntity<Map<String, String>> addIngredientsToFridge(@RequestBody Object requestBody) {
        logger.info("Entrée Fridge: " + requestBody);
        boolean success = fridgeService.saveFridgeItems(requestBody);

        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Ingrédients ajoutés avec succès");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "Erreur lors de l'ajout des ingrédients");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/fridge")
    public ResponseEntity<List<FridgeDto>> getFridgeItems() {
        List<FridgeDto> fridgeItems = fridgeService.getFridgeItems();
        return ResponseEntity.ok(fridgeItems);  // Typage fort avec List<FridgeDto>
    }

}
