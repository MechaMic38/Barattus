package com.mechamic38.barattus.gui.main;

import com.mechamic38.barattus.core.user.User;
import com.mechamic38.barattus.core.user.UserRole;
import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.ObjectProperty;

public interface IMainViewModel extends ViewModel {
    void logout();

    ObjectProperty<User> loggedUserProperty();

    ObjectProperty<UserRole> loggedUserRoleProperty();
}
