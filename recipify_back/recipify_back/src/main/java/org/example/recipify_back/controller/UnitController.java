package org.example.recipify_back.controller;

import org.example.recipify_back.entity.enumEntity.UnitOfMeasurement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UnitController {
    @GetMapping("/units")
    public List<UnitOfMeasurement>getAllUnits(){
        return List.of(UnitOfMeasurement.values());
    }
}
