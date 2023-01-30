package com.mechamic38.barattus.gui.common;

import com.mechamic38.barattus.core.common.ServiceProvider;
import com.mechamic38.barattus.gui.login.*;

public class ViewFactory {

    public static View createView(Views view) {
        return switch (view) {
            case LOGIN -> createLoginView();
            case REGISTER -> createRegistration();
            default -> throw new IllegalArgumentException("Invalid view type");
        };
    }

    private static View createLoginView() {
        ILoginViewModel viewModel = new LoginViewModel(
                ServiceProvider.getInstance().getLoginService()
        );
        return new LoginView(viewModel);
    }

    private static View createRegistration() {
        IRegistrationViewModel viewModel = new RegistrationViewModel(
                ServiceProvider.getInstance().getRegistrationService()
        );
        return new RegistrationView(viewModel);
    }
}
