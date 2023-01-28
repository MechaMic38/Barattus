package com.mechamic38.barattus.gui.login;

import com.mechamic38.barattus.gui.api.Context;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.NotNull;

/**
 * A LoginActivity can be used for starting easily a {@link LoginWindow} with a {@link LoginView}.
 *
 * <p>
 * It can be started by the {@link LoginActivity#show()}.
 */
public class LoginActivity {

    private final BooleanProperty showing;
    private LoginView loginView;


    public LoginActivity() {
        this.showing = new SimpleBooleanProperty();
        this.loginView = new LoginView();
    }

    public void show() {
        if (!this.isShowing()) {
            LoginWindow loginWindow = buildWindow();
            this.showing.bind(loginWindow.showingProperty());
            loginWindow.show();
        }
    }

    private LoginWindow buildWindow() {
        final var loginWindow = new LoginWindow(loginView);
        loginWindow.addEventHandler(WindowEvent.WINDOW_HIDDEN, event -> {
            this.loginView = null;
            this.showing.unbind();
            this.showing.set(false);
        });
        return loginWindow;
    }

    public boolean isShowing() {
        return showing.get();
    }

    public @NotNull Context getContext() {
        return loginView;
    }
}
