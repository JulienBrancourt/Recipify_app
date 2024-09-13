package org.example.recipify_back.service;

import org.example.recipify_back.entity.Diet;
import org.example.recipify_back.repository.DietRepository;
import org.springframework.stereotype.Service;

@Service
public class DietService {
    private final DietRepository dietRepository;

    public DietService(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    public Diet registerDiet(Diet diet) {
        diet.setDietName(diet.getDietName().toLowerCase());
        return dietRepository.save(diet);
    }

    public Diet getDiet(String dietName) {
        return dietRepository.findByDietName(dietName);
    }

    public Diet updateDiet(String dietName) {
        Diet diet = dietRepository.findByDietName(dietName);
        if (diet != null) {
            diet.setDietName(dietName);
            return dietRepository.save(diet);

        } else {
            throw new RuntimeException("Diet not found");
        }
    }

    public void deleteDiet(String dietName) {
        Diet diet = dietRepository.findByDietName(dietName);
        if (diet != null) {
            dietRepository.delete(diet);
        } else {
            throw new RuntimeException("Diet not found");
        }
    }

}
