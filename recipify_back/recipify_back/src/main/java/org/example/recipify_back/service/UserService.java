package org.example.recipify_back.service;

import org.example.recipify_back.entity.Allergy;
import org.example.recipify_back.entity.User;
import org.example.recipify_back.repository.AllergyRepository;
import org.example.recipify_back.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AllergyRepository allergyRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AllergyRepository allergyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.allergyRepository = allergyRepository;
    }

    public User registerUser(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        if (userRepository.findByUsername(user.getUsername().toLowerCase()).isPresent()) {
           // Return une Erreur 403
            return null;

        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        return userRepository.save(user);
    }

    public User addAllergyToUser(String userName, String allergyName) {
        User user = userRepository.findByUsername(userName.toLowerCase())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Allergy allergy = allergyRepository.findByAllergyName(allergyName.toLowerCase())
                .orElseThrow(() -> new RuntimeException("Allergy not found"));

        user.getAllergies().add(allergy);
        return userRepository.save(user);
    }
}
