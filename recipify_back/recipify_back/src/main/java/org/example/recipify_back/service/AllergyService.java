package org.example.recipify_back.service;

import org.example.recipify_back.entity.Allergy;
import org.example.recipify_back.repository.AllergyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (allergy.getAllergyName().isEmpty()) {
            throw new IllegalArgumentException("Allergy name cannot be empty");
        }
        allergy.setAllergyName(allergy.getAllergyName().toLowerCase());
        return allergyRepository.save(allergy);
    }

    public Allergy findAllergyByName(String allergyName) throws RuntimeException {
        Allergy allergy = allergyRepository.findByAllergyName(allergyName.toLowerCase()).orElseThrow(() -> new RuntimeException("Allergy Not Found"));
        if (allergy == null) {
            throw new RuntimeException("Allergy not found");
        }
        return allergy;
    }



    public Allergy updateAllergy(String allergyName) {
        Allergy allergy = allergyRepository.findByAllergyName(allergyName).orElseThrow(() -> new RuntimeException("Allergy Not Found"));
        if (allergy != null) {
            allergy.setAllergyName(allergyName);
            return allergyRepository.save(allergy);}
        else {
            throw new RuntimeException("Allergy not found");
        }
    }

    public void deleteAllergy(String allergyName) {
        Allergy allergyToDelete = allergyRepository.findByAllergyName(allergyName).orElseThrow(() -> new RuntimeException("Allergy Not Found"));
        if (allergyToDelete.getAllergyName().isEmpty()) {
            throw new IllegalArgumentException("Allergy name cannot be empty");
        } else {
            allergyRepository.delete(allergyToDelete);
        }
    }


}
