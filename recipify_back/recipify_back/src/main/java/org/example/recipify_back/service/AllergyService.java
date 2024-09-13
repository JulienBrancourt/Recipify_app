package org.example.recipify_back.service;

import org.example.recipify_back.entity.Allergy;
import org.example.recipify_back.repository.AllergyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllergyService {
    private AllergyRepository allergyRepository;

    public AllergyService(AllergyRepository allergyRepository) {
        this.allergyRepository = allergyRepository;
    }

    public List<Allergy> getAllAllergy() {
        return allergyRepository.findAll();
    }

    public Allergy addAllergy(Allergy allergy) {
        allergy.setAllergyName(allergy.getAllergyName().toLowerCase());
        return allergyRepository.save(allergy);
    }

    public Optional<Allergy> findAllergyByName(String allergyName) {
        return allergyRepository.findByAllergyName(allergyName.toLowerCase());
    }




}
