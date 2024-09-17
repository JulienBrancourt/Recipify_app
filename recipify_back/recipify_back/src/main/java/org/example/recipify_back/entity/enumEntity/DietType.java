package org.example.recipify_back.entity.enumEntity;

public enum DietType {
    VEGETARIEN("Végétarien"),
    VEGAN("Végan"),
    SANS_GLUTEN("Sans gluten"),
    SANS_LACTOSE("Sans lactose"),
    PALEO("Paléo"),
    KETO("Keto (cétogène)"),
    HALAL("Halal"),
    KASHER("Kasher"),
    PESCETARIEN("Pescétarien"),
    FLEXITARIEN("Flexitarien"),
    CRUDIVORE("Crudivore"),
    SANS_SUCRE("Sans sucre"),
    FRUITARIEN("Fruitarien");

    private final String displayName;

    DietType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}


