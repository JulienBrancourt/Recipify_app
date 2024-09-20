package org.example.recipify_back.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FridgeDto {
    private String name;
    private int quantity;
    private String unitOfMeasurement;
    private LocalDate expiration;

    @Override
    public String toString() {
        return "FridgeDto{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
                ", expiration=" + expiration +
                '}';
    }

}
