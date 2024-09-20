package org.example.recipify_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private int id;

    private String allergyName;

    @ManyToMany(mappedBy = "allergies")
    @JsonIgnore // Prevent infinite recursion during serialization
    private List<User> users;

    @Override
    public String toString() {
        return "Allergy{id=" + id + ", allergyName='" + allergyName + "'}";
    }

}



