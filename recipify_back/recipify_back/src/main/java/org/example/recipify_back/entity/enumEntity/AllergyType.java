package org.example.recipify_back.entity.enumEntity;

public enum AllergyType {
    GLUTEN("Gluten"),                 // Allergie au gluten
    LACTOSE("Lactose"),               // Allergie au lactose
    ARACHIDES("Arachides"),           // Allergie aux arachides
    FRUITS_A_COQUES("Fruits à coques"), // Allergie aux fruits à coques
    OEUFS("Œufs"),                    // Allergie aux œufs
    POISSON("Poisson"),               // Allergie au poisson
    SOJA("Soja"),                     // Allergie au soja
    CRUSTACES("Crustacés"),           // Allergie aux crustacés
    MOUTARDE("Moutarde");             // Allergie à la moutarde

    private final String displayName;

    AllergyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
