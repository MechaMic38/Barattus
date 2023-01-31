package com.mechamic38.barattus.core.user;

import com.mechamic38.barattus.util.Result;

import java.util.Objects;
import java.util.Set;

public class RegistrationService implements IRegistrationService {


    private final IUserRepository userRepository;

    public RegistrationService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result<User> registerUser(String username, String password, UserRole userRole) {
        if (userExists(username)) {
            return Result.error("signup.user.exists");
        }

        Set<UserPermission> perms = getUserPermissions(userRole);

        User user = new User(username, password, userRole, perms);
        userRepository.save(user);

        return Result.success(user);
    }

    /**
     * Gets the new user permissions, based on the type of user.
     *
     * @param userRole The user role
     * @return List of permissions
     */
    private Set<UserPermission> getUserPermissions(UserRole userRole) {
        return switch (userRole) {
            case END_USER -> Set.of(UserPermission.END_USER);
            case CONFIGURATOR -> Set.of(UserPermission.CONFIGURATOR);
            case MODERATOR -> Set.of(UserPermission.MODERATOR);
        };
    }

    private boolean userExists(String username) {
        return !Objects.isNull(userRepository.getById(username));
    }
}
