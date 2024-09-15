package org.example.recipify_back.entity.enumEntity;

import lombok.Getter;

@Getter
public enum UnitOfMeasurement {
    GRAMMES(1),         // 1 g = 1 g
    KILOGRAMMES(1000),  // 1 kg = 1000 g
    LITRES(1000),       // 1 litre = 1000 ml
    CENTILITRES(10),    // 1 cl = 10 ml
    MILLILITRES(1),     // 1 ml = 1 ml
    CUILLERES_A_SOUPE(15),  // 1 cuillère à soupe = 15 ml
    CUILLERES_A_CAFE(5),    // 1 cuillère à café = 5 ml
    TASSE(240),         // 1 tasse = 240 ml
    VERRE(200),         // 1 verre = 200 ml
    PINCEE(0.36),       // 1 pincée = environ 0.36 g
    GOUTTE(0.05);       // 1 goutte = environ 0.05 ml

    private final double conversionFactor;

    UnitOfMeasurement(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

      public double convertToStandard(double quantity) {
        return quantity * conversionFactor;
    }
}
