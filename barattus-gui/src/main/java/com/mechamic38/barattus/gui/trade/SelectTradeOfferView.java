package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.gui.util.GUIUtils;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectTradeOfferView extends BaseView implements Initializable {

    private final ISelectTradeOfferViewModel viewModel;

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
    private TableColumn<Offer, String> statusCol;

    public SelectTradeOfferView(ISelectTradeOfferViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onBack() {
        this.changeContent(Views.OFFER_DETAILS);
    }

    @FXML
    private void onProposeTrade() {
        if (viewModel.proposeTrade(
                offerTable.getSelectionModel().getSelectedItem()
        )) {
            getActivity().showInformationDialog(
                    I18N.getValue("trade.compatible.title"),
                    I18N.getValue("trade.create.success"),
                    buttonType -> changeContent(Views.TRADE_EDITOR)
            );
        }
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @Override
    public void onViewCreated() {
        viewModel.initialize();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setViewProperties();
        setCustomFactories();

        viewModel.errorProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) return;
            getActivity().showErrorDialog(
                    I18N.getValue("trade.compatible.title"),
                    I18N.getValue(newValue),
                    buttonType -> {
                        viewModel.errorProperty().set("");
                    }
            );
        });

        viewModel.otherOfferProperty().addListener((observable, oldValue, offer) -> {
            titleField.setText(offer.getOffer().getTitle());
            categoryField.setText(
                    GUIUtils.convertCategoryName(offer.getCategory())
            );
            usernameField.setText(offer.getOffer().getUserID());
        });

        offerTable.itemsProperty().bind(viewModel.offersProperty());
    }

    private void setViewProperties() {
        proposeTradeButton.disableProperty().bind(
                offerTable.getSelectionModel().selectedItemProperty().isNull()
        );
    }

    private void setCustomFactories() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        publishCol.setCellValueFactory(cell -> new SimpleStringProperty(
                GUIUtils.convertLocalDateTime(cell.getValue().getCreationDate())
        ));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
