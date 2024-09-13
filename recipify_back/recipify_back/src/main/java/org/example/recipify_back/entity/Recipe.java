package org.example.recipify_back.entity;


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
    private int id;
    private String title;

    @Column(unique = true)
    private String slug;
    private String instruction;
    private int calorie;

    @NonNull
    private int serving;
    private boolean is_approved;
    private boolean is_private;

    @ManyToMany(mappedBy = "favoriteRecipes")
    private List<User> usersWhoFavorited;

}
