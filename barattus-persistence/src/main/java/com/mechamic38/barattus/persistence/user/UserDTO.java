package com.mechamic38.barattus.persistence.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * User Data Transfer Object
 */
public class UserDTO {

    @SerializedName("id")
    private final String id;
    @SerializedName("username")
    private final String username;
    @SerializedName("password")
    private final String password;
    @SerializedName("role")
    private final String role;
    @SerializedName("permissions")
    private final List<String> permissions;


    public UserDTO(String id, String username, String password, String role, List<String> permissions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
