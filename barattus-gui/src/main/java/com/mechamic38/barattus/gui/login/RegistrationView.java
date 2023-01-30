package com.mechamic38.barattus.gui.login;

import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.SessionState;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationView extends BaseView implements Initializable {

    private final IRegistrationViewModel viewModel;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label userTypeLabel;


    public RegistrationView(IRegistrationViewModel viewModel) {
        super();
        this.viewModel = viewModel;
    }

    @FXML
    private void onBack() {
        SessionState.getInstance().setAdminMode(false);
        getActivity().setView(Views.LOGIN);
    }

    @FXML
    private void onSignup() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        viewModel.signupUser(username, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel.signedUpProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) return;
            getActivity().showInformationDialog(
                    I18N.getValue("signup.notification.signup"),
                    I18N.getValue("signup.notification.success"),
                    buttonType -> {
                        onBack();
                    }
            );
        });

        viewModel.errorMessageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) return;
            getActivity().showErrorDialog(
                    I18N.getValue("signup.notification.signup"),
                    I18N.getValue(newValue),
                    buttonType -> {
                    }
            );
            viewModel.errorMessageProperty().set("");
        });

        userTypeLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return I18N.getValue(viewModel.userTypeProperty().getValue().i18n);
        }, viewModel.userTypeProperty()));
    }
}
