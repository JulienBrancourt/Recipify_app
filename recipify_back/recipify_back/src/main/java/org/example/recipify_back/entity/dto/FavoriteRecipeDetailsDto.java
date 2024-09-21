package org.example.recipify_back.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoriteRecipeDetailsDto {
    private String title;
    private String slug;
    private String instruction;
}
