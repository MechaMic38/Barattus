package com.mechamic38.barattus.core.user;

public interface IUserService {

    boolean hasPermission(User user, UserPermission permission);

    UserRole getUserRole(User user);
}
