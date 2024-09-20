package org.example.recipify_back.repository;

import org.example.recipify_back.entity.FridgeItem;
import org.example.recipify_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FridgeRepository extends JpaRepository<FridgeItem, Integer> {
    FridgeItem findByUserId(int userId);
    List<FridgeItem> findByUser(User user);
}
