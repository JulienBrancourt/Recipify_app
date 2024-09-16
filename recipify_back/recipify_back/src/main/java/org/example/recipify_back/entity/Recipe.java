package org.example.recipify_back.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"id","slug"})
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;

    @Column(unique = true)
    private String slug;
    private String instruction;
    private int calorie;

    @NonNull
    private int serving;

    @Builder.Default
    @Column(name = "is_approved")
    private boolean isApproved = false;

    @Builder.Default
    @Column(name = "is_private")
    private boolean isPrivate = false;

    @ManyToMany(mappedBy = "favoriteRecipes")
    private List<User> usersWhoFavorited;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredient> recipeIngredients;


    public int getTotalCalories() {
        return recipeIngredients.stream()
                .mapToInt(ri -> {
                    double adjustedQuantity = ri.getUnit().convertToStandard(ri.getQuantity());
                    return (int)((ri.getIngredient().getCalorie() * adjustedQuantity) / 100);
                })
                .sum();
    }

}
