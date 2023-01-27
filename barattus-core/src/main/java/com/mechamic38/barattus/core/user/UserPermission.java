package com.mechamic38.barattus.core.user;

/**
 * Enum for the different {@link User} permissions.
 */
public enum UserPermission {
    CONFIGURATOR("user.type.configurator"),
    END_USER("user.type.end_user"),
    MODERATOR("user.type.moderator");

    private final String i18n;


    UserPermission(String i18n) {
        this.i18n = i18n;
    }

    @Override
    public String toString() {
        //TODO: I18N Reference
        return super.toString();
    }
}
