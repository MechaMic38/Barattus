package com.mechamic38.barattus.gui.login;

import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginView extends BaseView implements Initializable {

    private final ILoginViewModel viewModel;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;


    public LoginView(ILoginViewModel viewModel) {
        super();
        this.viewModel = viewModel;
    }

    @FXML
    private void onLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        viewModel.loginUser(username, password);
    }

    @FXML
    private void onSignup() {
        getActivity().setView(Views.REGISTER);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel.loggedInProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) return;
            //TODO Change view to Main
            getActivity().showInformationDialog(
                    I18N.getValue("login.notification.login"),
                    I18N.getValue(
                            "login.notification.success",
                            viewModel.loggedUserProperty().getValue().getUsername()
                    ),
                    buttonType -> {
                        viewModel.loggedInProperty().set(false);
                    }
            );
        });

        viewModel.configMatchProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) return;
            getActivity().showInformationDialog(
                    I18N.getValue("login.notification.login"),
                    I18N.getValue("login.notification.config.match"),
                    buttonType -> {
                        viewModel.configMatchProperty().set(false);
                        getActivity().setView(Views.REGISTER);
                    }
            );
        });

        viewModel.errorMessageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) return;
            getActivity().showErrorDialog(
                    I18N.getValue("login.error.error"),
                    I18N.getValue(newValue),
                    buttonType -> {
                        viewModel.errorMessageProperty().set("");
                    }
            );
        });
    }
}
