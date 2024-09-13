package org.example.recipify_back.repository;

import org.example.recipify_back.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AllergyRepository extends JpaRepository<Allergy, Long> {
    Optional<Allergy> findByAllergyName(String allergyName);
}
