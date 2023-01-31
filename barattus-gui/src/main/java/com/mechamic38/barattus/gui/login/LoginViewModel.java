package com.mechamic38.barattus.gui.login;

import com.mechamic38.barattus.core.user.ILoginService;
import com.mechamic38.barattus.core.user.User;
import com.mechamic38.barattus.gui.common.SessionState;
import javafx.beans.property.*;

public class LoginViewModel implements ILoginViewModel {

    private final ILoginService loginService;

    private final ObjectProperty<User> loggedUser = new SimpleObjectProperty<>(null);
    private final BooleanProperty configMatch = new SimpleBooleanProperty(false);
    private final BooleanProperty loggedIn = new SimpleBooleanProperty(false);
    private final StringProperty errorMessage = new SimpleStringProperty("");

    public LoginViewModel(ILoginService loginService) {
        this.loginService = loginService;
    }


    @Override
    public void loginUser(String username, String password) {
        ILoginService.LoginResult result = loginService.loginUser(username, password);

        if (result.isError()) {
            errorMessage.set(result.getError());
        } else if (result.isConfigMatch()) {
            SessionState.getInstance().setAdminMode(true);
            configMatch.set(true);
        } else {
            SessionState.getInstance().setUser(result.getUser());
            loggedUser.set(result.getUser());
            loggedIn.set(true);
        }
    }

    @Override
    public ObjectProperty<User> loggedUserProperty() {
        return loggedUser;
    }

    @Override
    public BooleanProperty configMatchProperty() {
        return configMatch;
    }

    @Override
    public BooleanProperty loggedInProperty() {
        return loggedIn;
    }

    @Override
    public StringProperty errorMessageProperty() {
        return errorMessage;
    }

    @Override
    public void initialize() {

    }
}
