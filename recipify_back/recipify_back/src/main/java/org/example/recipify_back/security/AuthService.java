package org.example.recipify_back.security;

import org.example.recipify_back.entity.User;
import org.example.recipify_back.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalArgumentException("User not found");
        }

        String userName = authentication.getName();

        if (userName == null) {
            throw new RuntimeException("User not authenticated");
        }

        // Recherche de l'utilisateur dans la base de données

        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
