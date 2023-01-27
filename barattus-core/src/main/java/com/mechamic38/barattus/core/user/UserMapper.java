package com.mechamic38.barattus.core.user;

import com.mechamic38.barattus.persistence.user.UserDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mapper class for users.
 */
class UserMapper {

    /**
     * Converts the given User entity into a UserDTO object.
     *
     * @param user User entity to map
     * @return UserDTO object
     */
    public UserDTO toDto(User user) {
        List<String> permissions = user.getPermissions().stream()
                .map(UserPermission::name)
                .collect(Collectors.toList());

        return new UserDTO(
                user.getID().toString(),
                user.getUsername(),
                user.getPassword(),
                permissions
        );
    }

    /**
     * Converts the given UserDTO object into a User entity.
     *
     * @param userDTO UserDTO object to map
     * @return User entity
     */
    public User fromDto(UserDTO userDTO) {
        List<UserPermission> permissions = userDTO.getPermissions().stream()
                .map(UserPermission::valueOf)
                .collect(Collectors.toList());

        User user = new User(
                UUID.fromString(userDTO.getId()),
                userDTO.getUsername(),
                userDTO.getPassword()
        );
        user.addPermissions(permissions);

        return user;
    }
}
