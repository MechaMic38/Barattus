package com.mechamic38.barattus.gui.login;

import com.mechamic38.barattus.gui.window.BaseWindow;
import com.mechamic38.barattus.i18n.api.I18N;

public class LoginWindow extends BaseWindow<LoginView> {


    public LoginWindow(LoginView loginView) {
        super(
                I18N.getValue("window.login.title"),
                loginView
        );
    }
}
