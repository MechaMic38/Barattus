package com.mechamic38.barattus.gui.login;

import com.mechamic38.barattus.core.user.User;
import com.mechamic38.barattus.gui.common.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface ILoginViewModel extends ViewModel {

    void loginUser(String username, String password);

    public ObjectProperty<User> loggedUserProperty();

    public BooleanProperty configMatchProperty();

    public BooleanProperty loggedInProperty();

    public StringProperty errorMessageProperty();
}
