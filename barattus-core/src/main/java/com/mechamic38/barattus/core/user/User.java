package com.mechamic38.barattus.core.user;

import com.mechamic38.barattus.core.common.Entity;
import com.mechamic38.barattus.util.ListUtils;

import java.util.*;

/**
 * User of the application.
 */
public class User extends Entity<String> {

    private final String username;
    private final String password;
    private final Set<UserPermission> permissions;
    private UserRole role;


    /**
     * Creates a new user with the given username, password, role and permissions.
     *
     * @param username    Name of the user
     * @param password    Password of the user
     * @param role        Role of the user
     * @param permissions Permissions of the user
     */
    public User(String username, String password, UserRole role, Set<UserPermission> permissions) {
        this(username, password, role);
        this.addPermissions(permissions);
    }

    /**
     * Creates a new user with the given username, password, and role.
     *
     * @param username Name of the user
     * @param password Password of the user
     * @param role     Role of the user
     */
    public User(String username, String password, UserRole role) {
        this(username, password);
        this.role = role;
    }

    /**
     * Creates a new user with the given username and password.
     *
     * @param username Name of the user
     * @param password Password of the user
     */
    private User(String username, String password) {
        if (Objects.isNull(username) || username.isBlank())
            throw new IllegalArgumentException("Username is empty!");

        if (Objects.isNull(password) || password.isBlank())
            throw new IllegalArgumentException("Password is empty!");

        this.id = username;
        this.username = username;
        this.password = password;
        this.permissions = new LinkedHashSet<>();
    }

    private User(String id, String username, String password, UserRole role, Set<UserPermission> permissions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.permissions = ListUtils.copy(permissions);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(UserPermission permission) {
        return permissions.contains(permission);
    }

    /**
     * Adds the given permissions to the user.
     *
     * @param permissions List of user permissions
     */
    public void addPermissions(UserPermission... permissions) {
        this.permissions.addAll(List.of(permissions));
    }

    /**
     * Adds the given permissions to the user.
     *
     * @param permissions List of user permissions
     */
    public void addPermissions(Collection<UserPermission> permissions) {
        this.permissions.addAll(permissions);
    }

    /**
     * Adds a permission to the user.
     *
     * @param permission User Permission
     */
    public boolean addPermission(UserPermission permission) {
        return permissions.add(permission);
    }

    /**
     * Removes the given permissions to the user.
     *
     * @param permissions List of user permissions
     */
    public void removePermissions(UserPermission... permissions) {
        this.permissions.removeAll(List.of(permissions));
    }

    /**
     * Removes the given permissions to the user.
     *
     * @param permissions List of user permissions
     */
    public void removePermissions(Collection<UserPermission> permissions) {
        this.permissions.removeAll(permissions);
    }

    /**
     * Removes a permission to the user.
     *
     * @param permission User permission
     */
    public boolean removePermission(UserPermission permission) {
        return permissions.remove(permission);
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public User clone() {
        return new User(
                id,
                username,
                password,
                role,
                permissions
        );
    }
}
