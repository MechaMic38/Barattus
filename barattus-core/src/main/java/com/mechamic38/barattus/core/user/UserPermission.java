package com.mechamic38.barattus.core.user;

/**
 * Enum for the different {@link User} permissions.
 */
public enum UserPermission {
    CONFIGURATOR("user.permission.configurator"),
    END_USER("user.permission.enduser"),
    MODERATOR("user.permission.moderator");

    public final String i18n;


    UserPermission(String i18n) {
        this.i18n = i18n;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
