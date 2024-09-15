package org.example.recipify_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.recipify_back.entity.enumEntity.IngredientCategory;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ingredientName;
    private int calorie;

    @Enumerated(EnumType.STRING)
    private IngredientCategory ingredientCategory;

    @OneToMany(mappedBy = "ingredient")
    private List<FridgeItem> fridgeItems;
}
