package org.example.recipify_back.security;

import org.example.recipify_back.entity.User;
import org.example.recipify_back.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
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

        String specificRole = "ROLE_ADMIN"; // Le rôle spécifique que vous cherchez

        boolean hasSpecificRole = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(specificRole));

        if (hasSpecificRole) {
            log.info("L'utilisateur a le rôle spécifique : " + specificRole);
        } else {
            log.info("L'utilisateur n'a pas le rôle spécifique : " + specificRole);
        }
        // Recherche de l'utilisateur dans la base de données

        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
