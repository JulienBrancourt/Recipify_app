package org.example.recipify_back.repository;

import org.example.recipify_back.entity.Recipe;
import org.example.recipify_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u JOIN u.favoriteRecipes fr WHERE fr = :recipe")
    List<User> findUsersByFavoriteRecipesContaining(@Param("recipe") Recipe recipe);
}
