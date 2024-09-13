package org.example.recipify_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String allergyName;

    @ManyToMany(mappedBy = "allergies")
    @JsonBackReference
    private List<User> users;
}


