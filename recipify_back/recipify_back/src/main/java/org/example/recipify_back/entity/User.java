package org.example.recipify_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @ManyToMany
    @JoinTable(
            name = "user_allergy",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "allergy_id")
    )
    @JsonIgnore // Avoid circular references
    private List<Allergy> allergies;

    @ManyToMany
    @JoinTable(
            name = "user_favorite_recipes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    @JsonIgnore // Avoid circular references
    private List<Recipe> favoriteRecipes;

    @OneToMany(mappedBy = "user")
    @JsonIgnore // Avoid circular references for FridgeItems
    private List<FridgeItem> fridgeItems;

    @ManyToMany
    @JoinTable(
            name = "user_diet",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "diet_id")
    )
    @JsonIgnore // Avoid circular references
    private List<Diet> diets;

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "'}";
    }

}
