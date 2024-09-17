package org.example.recipify_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.recipify_back.entity.enumEntity.IngredientCategory;
import org.example.recipify_back.entity.enumEntity.UnitOfMeasurement;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    private String ingredientName;
    private int calorie;

    @Enumerated(EnumType.STRING)
    private IngredientCategory ingredientCategory;

    @Enumerated(EnumType.STRING)
    private UnitOfMeasurement unitOfMeasurement;

    @OneToMany(mappedBy = "ingredient")
    @JsonIgnore
    private List<FridgeItem> fridgeItems;
}
