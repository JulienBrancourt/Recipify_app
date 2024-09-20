package org.example.recipify_back.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.recipify_back.entity.enumEntity.UnitOfMeasurement;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private UnitOfMeasurement unit;

    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "id=" + id +
                ", recipe=" + recipe +
                ", ingredient=" + ingredient +
                ", quantity=" + quantity +
                ", unit=" + unit +
                '}';
    }
}
