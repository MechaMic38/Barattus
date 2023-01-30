package com.mechamic38.barattus.core.user;

public class UserService implements IUserService {

    public UserService() {

    }

    @Override
    public boolean hasPermission(User user, UserPermission permission) {
        return user.hasPermission(permission);
    }

    @Override
    public UserRole getUserRole(User user) {
        if (user.getPermissions().contains(UserPermission.CONFIGURATOR)) {
            return UserRole.CONFIGURATOR;
        }
        if (user.getPermissions().contains(UserPermission.MODERATOR)) {
            return UserRole.MODERATOR;
        }
        return UserRole.END_USER;
    }
}
