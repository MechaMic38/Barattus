package com.mechamic38.barattus.core.user;

/**
 * Enum for the different {@link User} roles.
 */
public enum UserRole {
    CONFIGURATOR("user.role.configurator"),
    END_USER("user.role.enduser"),
    MODERATOR("user.role.moderator");

    public final String i18n;


    UserRole(String i18n) {
        this.i18n = i18n;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
