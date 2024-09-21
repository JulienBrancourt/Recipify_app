package org.example.recipify_back.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    private String title;
    private String slug;
    private String instruction;
    private int calorie;
    private int serving;
    private List<IngredientDto> recipeIngredients;
}
