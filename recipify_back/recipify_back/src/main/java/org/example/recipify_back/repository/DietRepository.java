package org.example.recipify_back.repository;

import org.example.recipify_back.entity.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DietRepository extends JpaRepository<Diet, Long> {

}
