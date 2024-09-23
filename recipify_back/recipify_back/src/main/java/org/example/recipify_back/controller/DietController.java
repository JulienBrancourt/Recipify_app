package org.example.recipify_back.controller;

import org.example.recipify_back.entity.Diet;
import org.example.recipify_back.service.DietService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DietController {

    private final DietService dietService;

    public DietController(DietService dietService) {
        this.dietService = dietService;
    }


    @PostMapping("/diet")
    public ResponseEntity<?> registerDiet(@RequestBody Diet diet) {
        if(diet.getDietName().isEmpty()){
            return new ResponseEntity<>("Diet must not be empty", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(dietService.registerDiet(diet));
    }

    @GetMapping("/diet/{dietName}")
    public Diet getDiet(@PathVariable String dietName) {
        return dietService.getDiet(dietName);
    }

    @GetMapping("/diets")
        public List<Diet> getAllDiets(){
            return dietService.getAllDiets();
        }


    @PutMapping("/diet/{dietName}")
    public void updateDiet(@RequestBody Diet diet) throws RuntimeException{
        dietService.updateDiet(diet.getDietName());
    }

    @DeleteMapping("/diet/{dietName}")
    public void deleteDiet(@PathVariable String dietName) throws RuntimeException {
        dietService.deleteDiet(dietName);
    }
}
