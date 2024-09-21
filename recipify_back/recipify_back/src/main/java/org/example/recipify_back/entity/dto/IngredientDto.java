package org.example.recipify_back.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {
    private String ingredientName;
    private int calorie;
    private String ingredientCategory;
}

