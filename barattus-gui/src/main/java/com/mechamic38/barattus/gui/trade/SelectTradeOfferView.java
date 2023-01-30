package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class SelectTradeOfferView extends BaseView implements Initializable {

    private final ISelectTradeOfferViewModel viewModel;
    private Consumer<Views> viewChangeAction;

    @FXML
    private ScrollPane graphic;

    @FXML
    private Button backButton;
    @FXML
    private Button proposeTradeButton;

    @FXML
    private TextField titleField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField usernameField;

    @FXML
    private TableView<Offer> offerTable;
    @FXML
    private TableColumn<Offer, String> titleCol;
    @FXML
    private TableColumn<Offer, String> publishCol;
    @FXML
    private TableColumn<Offer, String> categoryCol;

    public SelectTradeOfferView(ISelectTradeOfferViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onBack() {
        this.changeContent(Views.OFFER_DETAILS);
    }

    @FXML
    private void onProposeTrade() {
        /*controller.setInitiatorOffer(
                offerTable.getSelectionModel().getSelectedItem()
        );

        Alert dialog;
        if (controller.proposeTrade()) {
            dialog = new Alert(
                    Alert.AlertType.INFORMATION,
                    presenter.getMessage(),
                    ButtonType.OK
            );
            dialog.showAndWait();
            this.changeContent(Views.TRADE_EDITOR);
        } else {
            dialog = new Alert(
                    Alert.AlertType.WARNING,
                    presenter.getError(),
                    ButtonType.OK
            );
            dialog.showAndWait();
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
