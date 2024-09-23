package org.example.recipify_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    private String dietName;

    @ManyToMany(mappedBy = "diets")
    @JsonIgnore
    private List<User> users;

    @Override
    public String toString() {
        return "Diet{id=" + id + ", dietName='" + dietName + "'}";
    }
}
