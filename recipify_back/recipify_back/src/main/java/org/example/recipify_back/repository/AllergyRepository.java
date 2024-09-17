package org.example.recipify_back.repository;

import org.example.recipify_back.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AllergyRepository extends JpaRepository<Allergy, Integer> {
    Optional<Allergy> findByAllergyName(String allergyName);
    List<Allergy> findByAllergyNameIn(List<String> allergyNames);
}
