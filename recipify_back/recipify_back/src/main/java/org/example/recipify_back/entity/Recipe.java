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

    @Builder.Default
    @Column(name = "is_approved")
    private boolean isApproved = false;

    @Builder.Default
    @Column(name = "is_private")
    private boolean isPrivate = false;

    @ManyToMany(mappedBy = "favoriteRecipes")
    private List<User> usersWhoFavorited;

}
