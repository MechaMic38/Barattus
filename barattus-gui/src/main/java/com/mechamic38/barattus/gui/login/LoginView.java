package com.mechamic38.barattus.gui.login;

import com.mechamic38.barattus.gui.common.BaseView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginView extends BaseView implements Initializable {

    private final LoginViewModel viewModel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;


    public LoginView(LoginViewModel viewModel) {
        super();
        this.viewModel = viewModel;
    }

    @FXML
    private void onLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        /*switch (viewModel.loginUser(username, password)) {

            case CONFIG_MATCH:
                this.changeScene(Scenes.REGISTRATION);
                break;

            case SUCCESS:
                dialog = new Alert(
                        Alert.AlertType.INFORMATION,
                        presenter.getMessage(),
                        ButtonType.OK
                );
                dialog.showAndWait();
                this.changeScene(Scenes.MAIN_VIEW);
                break;

            case FAIL:
                dialog = new Alert(
                        Alert.AlertType.WARNING,
                        presenter.getError(),
                        ButtonType.OK
                );
                dialog.showAndWait();
                break;

            default:
                throw new IllegalArgumentException("Unknown login status category");

        }*/
    }

    @FXML
    private void onSignup() {

    }

    public void reload() {
        usernameField.clear();
        passwordField.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
