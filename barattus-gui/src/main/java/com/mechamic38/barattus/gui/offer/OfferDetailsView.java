package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class OfferDetailsView extends BaseView implements Initializable {

    private final IOfferDetailsViewModel viewModel;
    private Consumer<Views> viewChangeAction;

    @FXML
    private ScrollPane graphic;
    @FXML
    private GridPane fieldContainer;

    @FXML
    private Button backButton;
    @FXML
    private Button proposeTradeButton;
    @FXML
    private Button withdrawButton;

    @FXML
    private TextField titleField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField creationDateField;
    @FXML
    private TextField statusField;

    public OfferDetailsView(IOfferDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onBack() {
        this.changeContent(Views.OFFER_LIST);
    }

    @FXML
    private void onPropose() {
        this.changeContent(Views.SELECT_TRADE_OFFER);
    }

    @FXML
    private void onWithdraw() {
        /*Alert dialog = new Alert(
                Alert.AlertType.WARNING,
                presenter.getMessage("withdrawConfirmation"),
                ButtonType.NO,
                ButtonType.YES
        );
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.YES) {
            controller.withdrawOffer();
            reload();
        }*/
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @Override
    public void changeContent(Views view) {
        if (viewChangeAction != null) viewChangeAction.accept(view);
    }

    @Override
    public void setViewChangeAction(Consumer<Views> viewChangeAction) {
        this.viewChangeAction = viewChangeAction;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
}
