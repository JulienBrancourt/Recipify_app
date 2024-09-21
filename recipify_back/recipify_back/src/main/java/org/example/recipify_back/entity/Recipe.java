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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @JsonIgnore
    private int id;

    private String title;

    @Column(unique = true)
    private String slug;
    private String instruction;
    private int calorie;

    private int time;

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

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", instruction='" + instruction + '\'' +
                ", calorie=" + calorie +
                ", serving=" + serving +
                ", isApproved=" + isApproved +
                ", isPrivate=" + isPrivate +
                ", creator=" + creator +
                '}';
    }

    public int getTotalCalories() {
        return recipeIngredients.stream()
                .mapToInt(ri -> {
                    double adjustedQuantity = ri.getUnit().convertToStandard(ri.getQuantity());
                    return (int)((ri.getIngredient().getCalorie() * adjustedQuantity) / 100);
                })
                .sum();
    }
}
