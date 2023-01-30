package com.mechamic38.barattus.gui.login;

import com.mechamic38.barattus.gui.common.BaseActivity;
import com.mechamic38.barattus.gui.common.View;
import com.mechamic38.barattus.gui.common.Views;

/**
 * A LoginActivity can be used for starting easily a {@link LoginView}.
 *
 * <p>
 * It can be started by the {@link LoginActivity#show()}.
 */
public class LoginActivity extends BaseActivity {

    private View view;


    public LoginActivity() {
        super();
        onCreate();
    }

    @Override
    protected void onCreate() {
        setView(Views.LOGIN);
    }

    /*public void show() {
        if (!this.isShowing()) {
            LoginWindow loginWindow = buildWindow();
            this.showing.bind(loginWindow.showingProperty());
            loginWindow.show();
        }
    }*/

    /*private LoginWindow buildWindow() {
        final var loginWindow = new LoginWindow(loginView);
        loginWindow.addEventHandler(WindowEvent.WINDOW_HIDDEN, event -> {
            this.loginView = null;
            this.showing.unbind();
            this.showing.set(false);
        });
        return loginWindow;
    }*/
}
