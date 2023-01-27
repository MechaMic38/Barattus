package com.mechamic38.barattus.core;

import com.mechamic38.barattus.core.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Set;

import static com.mechamic38.barattus.core.user.UserPermission.CONFIGURATOR;
import static com.mechamic38.barattus.core.user.UserPermission.END_USER;

public class UserTest {

    @Test
    public void cannot_create_user_without_username() {
        Executable action = () -> new User("", "1234");
        Assertions.assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    public void cannot_create_user_without_password() {
        Executable action = () -> new User("Mecha", "");
        Assertions.assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    public void adding_permissions_updates_permission_list() {
        var user = new User("Mecha", "1234");

        user.addPermission(CONFIGURATOR);

        Assertions.assertEquals(Set.of(CONFIGURATOR), user.getPermissions());
    }

    @Test
    public void removing_permissions_removes_permissions_from_list() {
        var user = new User("Mecha", "1234");
        user.addPermissions(CONFIGURATOR, END_USER);

        user.removePermission(END_USER);

        Assertions.assertEquals(Set.of(CONFIGURATOR), user.getPermissions());
    }
}
