package org.example.recipify_back.entity.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    private String username;
    private String password;
    private List<String> allergies;
    private List<String> diets;

    @Override
    public String toString() {
        return "UserRegistrationDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", allergies=" + allergies +
                ", diets=" + diets +
                '}';
    }
}