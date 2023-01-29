package com.mechamic38.barattus.gui.common;

import com.mechamic38.barattus.gui.login.LoginView;
import com.mechamic38.barattus.gui.login.LoginViewModel;
import com.mechamic38.barattus.gui.login.RegistrationView;
import com.mechamic38.barattus.gui.login.RegistrationViewModel;

public class ViewFactory {

    public enum Views {
        LOGIN("Login"),
        REGISTER("Registration");

        public final String fxml;

        Views(String fxml) {
            this.fxml = fxml;
        }
    }

    public static View createView(Views view) {
        return switch (view) {
            case LOGIN -> createLoginView();
            case REGISTER -> createRegistration();
            default -> throw new IllegalArgumentException("Invalid view type");
        };
    }

    private static View createLoginView() {
        LoginViewModel viewModel = new LoginViewModel();
        return new LoginView(viewModel);
    }

    private static View createRegistration() {
        RegistrationViewModel viewModel = new RegistrationViewModel();
        return new RegistrationView(viewModel);
    }
}
