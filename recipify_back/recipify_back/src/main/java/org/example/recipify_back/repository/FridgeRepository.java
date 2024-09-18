package org.example.recipify_back.repository;

import org.example.recipify_back.entity.FridgeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<FridgeItem, Integer> {
    FridgeItem findByUserId(int userId);
}
