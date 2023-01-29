package com.mechamic38.barattus.gui.login;
import com.mechamic38.barattus.gui.common.BaseView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationView extends BaseView implements Initializable {

    private final RegistrationViewModel viewModel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label userTypeLabel;


    public RegistrationView(RegistrationViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onBack() {

    }

    @FXML
    private void onSignup() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        /*switch (controller.createUser(username, password)) {

            case SUCCESS:
                dialog = new Alert(
                        Alert.AlertType.INFORMATION,
                        presenter.getMessage(),
                        ButtonType.OK
                );
                dialog.showAndWait();
                this.changeScene(Scenes.LOGIN);
                break;

            case FAIL:

            case ALREADY_EXISTS:
                dialog = new Alert(Alert.AlertType.WARNING,
                        presenter.getError(),
                        ButtonType.OK
                );
                dialog.showAndWait();
                break;

            default:
                throw new IllegalArgumentException("Unknown registration status category");

        }*/
    }

    public void reload() {
        usernameField.clear();
        passwordField.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*try {

            userTypeLabel.textProperty().bind(
                    ReadOnlyJavaBeanStringPropertyBuilder.create()
                            .bean(presenter).name("userType").build()
            );

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }*/
    }
}
