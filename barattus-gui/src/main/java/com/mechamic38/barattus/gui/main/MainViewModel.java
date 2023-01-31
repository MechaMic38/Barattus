package com.mechamic38.barattus.gui.main;

import com.mechamic38.barattus.core.user.IUserService;
import com.mechamic38.barattus.core.user.User;
import com.mechamic38.barattus.core.user.UserRole;
import com.mechamic38.barattus.gui.common.SessionState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class MainViewModel implements IMainViewModel {

    private final ObjectProperty<User> loggedUser = new SimpleObjectProperty<>(null);
    private final ObjectProperty<UserRole> loggedUserRole = new SimpleObjectProperty<>(null);

    private final IUserService userService;

    public MainViewModel(IUserService userService) {
        this.userService = userService;
        loadProperties();
    }

    private void loadProperties() {
        User user = SessionState.getInstance().getUser();
        loggedUser.set(user);

        UserRole userRole = user.getRole();
        loggedUserRole.set(userRole);
    }

    @Override
    public void logout() {
        SessionState.getInstance().reset();
    }

    @Override
    public ObjectProperty<User> loggedUserProperty() {
        return loggedUser;
    }

    @Override
    public ObjectProperty<UserRole> loggedUserRoleProperty() {
        return loggedUserRole;
    }

    @Override
    public void initialize() {

    }
}
