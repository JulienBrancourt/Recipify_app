package org.example.recipify_back.controller;

import org.example.recipify_back.entity.Diet;
import org.example.recipify_back.service.DietService;
import org.springframework.web.bind.annotation.*;

@RestController
public class DietController {
    private final DietService dietService;

    public DietController(DietService dietService) {
        this.dietService = dietService;
    }


    @PostMapping("/diet")
    public void registerDiet(@RequestBody Diet diet) {
        dietService.registerDiet(diet);
    }

    @GetMapping("/diet/{dietName}")
    public Diet getDiet(@PathVariable String dietName) {
        return dietService.getDiet(dietName);
    }

    @PutMapping("/diet/{dietName}")
    public void updateDiet(@RequestBody Diet diet) {
        dietService.updateDiet(diet.getDietName());
    }

    @DeleteMapping("/diet/{dietName}")
    public void deleteDiet(@PathVariable String dietName) {
        dietService.deleteDiet(dietName);
    }
}
