package com.mechamic38.barattus.gui.login;

import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.gui.main.MainActivity;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class LoginView extends BaseView implements Initializable {

    private final ILoginViewModel viewModel;

    @FXML
    protected GridPane graphic;
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
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

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
            Platform.runLater(() -> new MainActivity().show());
            getActivity().close();
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

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @Override
    public void onViewCreated() {
        viewModel.initialize();
    }
}
