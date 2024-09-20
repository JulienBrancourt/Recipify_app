package org.example.recipify_back.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    private String title;

    @Column(unique = true)
    private String slug;
    private String instruction;
    private int calorie;

    private int serving;

    @Builder.Default
    @Column(name = "is_approved")
    private boolean isApproved = false;

    @Builder.Default
    @Column(name = "is_private")
    private boolean isPrivate = false;

    @ManyToMany(mappedBy = "favoriteRecipes")
    @JsonIgnore // Avoid circular references
    private List<User> usersWhoFavorited;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredient> recipeIngredients;


    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;


    public int getTotalCalories() {
        return recipeIngredients.stream()
                .mapToInt(ri -> {
                    double adjustedQuantity = ri.getUnit().convertToStandard(ri.getQuantity());
                    return (int)((ri.getIngredient().getCalorie() * adjustedQuantity) / 100);
                })
                .sum();
    }
}
