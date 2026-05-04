package fr.formation.backend.config;

public enum RoleEnum {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private final String role;

    public String getRole() {
        return role;
    }

    private RoleEnum(String role) {
        this.role = role;
    }
}
