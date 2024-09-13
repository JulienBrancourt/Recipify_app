package org.example.recipify_back.service;

import org.example.recipify_back.entity.Allergy;
import org.example.recipify_back.repository.AllergyRepository;

import java.util.List;
import java.util.Optional;

public class AllergyService {
    private AllergyRepository allergyRepository;

    public AllergyService(AllergyRepository allergyRepository) {
        this.allergyRepository = allergyRepository;
    }

    public List<Allergy> getAllAllergy() {
        return allergyRepository.findAll();
    }

    public Allergy addAllergy(Allergy allergy) {
        return allergyRepository.save(allergy);
    }

    public Optional<Allergy> findAllergyByName(String allergyName) {
        return allergyRepository.findByAllergyName(allergyName);
    }
}
