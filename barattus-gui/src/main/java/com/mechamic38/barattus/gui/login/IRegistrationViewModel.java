package com.mechamic38.barattus.gui.login;

import com.mechamic38.barattus.core.user.UserRole;
import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface IRegistrationViewModel extends ViewModel {

    void signupUser(String username, String password);

    ObjectProperty<UserRole> userTypeProperty();

    BooleanProperty signedUpProperty();

    StringProperty errorMessageProperty();
}
