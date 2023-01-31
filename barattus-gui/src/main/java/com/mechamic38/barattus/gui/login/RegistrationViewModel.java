package com.mechamic38.barattus.gui.login;

import com.mechamic38.barattus.core.user.IRegistrationService;
import com.mechamic38.barattus.core.user.User;
import com.mechamic38.barattus.core.user.UserRole;
import com.mechamic38.barattus.gui.common.SessionState;
import com.mechamic38.barattus.util.Result;
import javafx.beans.property.*;

public class RegistrationViewModel implements IRegistrationViewModel {

    private final IRegistrationService registrationService;

    private final ObjectProperty<UserRole> userType = new SimpleObjectProperty<>(UserRole.END_USER);
    private final BooleanProperty signedUp = new SimpleBooleanProperty(false);
    private final StringProperty errorMessage = new SimpleStringProperty("");

    public RegistrationViewModel(IRegistrationService registrationService) {
        this.registrationService = registrationService;

        if (SessionState.getInstance().isAdminMode()) {
            userType.set(UserRole.CONFIGURATOR);
        } else {
            userType.set(UserRole.END_USER);
        }
    }

    @Override
    public void signupUser(String username, String password) {
        Result<User> result = registrationService.registerUser(username, password, userType.get());

        if (result.isError()) {
            errorMessage.set(result.getError());
        } else {
            SessionState.getInstance().setAdminMode(false);
            signedUp.set(true);
        }
    }

    @Override
    public ObjectProperty<UserRole> userTypeProperty() {
        return userType;
    }

    @Override
    public BooleanProperty signedUpProperty() {
        return signedUp;
    }

    @Override
    public StringProperty errorMessageProperty() {
        return errorMessage;
    }

    @Override
    public void initialize() {

    }
}
