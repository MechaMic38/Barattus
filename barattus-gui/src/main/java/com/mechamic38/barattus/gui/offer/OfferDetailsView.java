package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.offer.OfferField;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.gui.util.GUIUtils;
import com.mechamic38.barattus.gui.util.I18NButtonTypes;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;

public class OfferDetailsView extends BaseView implements Initializable {

    private final IOfferDetailsViewModel viewModel;
    private final HashMap<OfferField, TextField> offerFields;

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
        this.offerFields = new LinkedHashMap<>();
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
        getActivity().showConfirmationDialog(
                I18N.getValue("offer.details.title"),
                I18N.getValue("offer.withdraw.confirmation"),
                buttonType -> {
                    if (buttonType == I18NButtonTypes.YES) {
                        viewModel.withdrawOffer();
                    }
                }
        );
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
        setOwnerProperties();
        setViewProperties();
        setCustomFactories();

        viewModel.errorProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) return;
            getActivity().showErrorDialog(
                    I18N.getValue("offer.details.title"),
                    I18N.getValue(newValue),
                    buttonType -> {
                        viewModel.errorProperty().set("");
                    }
            );
        });

        viewModel.offerProperty().addListener((observable, oldValue, offerData) -> {
            titleField.setText(offerData.getOffer().getTitle());
            categoryField.setText(
                    GUIUtils.convertCategoryName(offerData.getCategory())
            );
            usernameField.setText(offerData.getOffer().getUserID());
            creationDateField.setText(
                    GUIUtils.convertLocalDateTime(offerData.getOffer().getCreationDate())
            );
            statusField.setText(I18N.getValue(offerData.getOffer().getStatus().i18n));

            createFields(offerData.getOffer().getOfferFields());
        });
    }

    private void setOwnerProperties() {
        withdrawButton.disableProperty().bind(viewModel.withdrawableProperty().not());
        proposeTradeButton.disableProperty().bind(viewModel.proposableProperty().not());

        setNodeVisibility(withdrawButton, viewModel.ownerProperty());
        setNodeVisibility(proposeTradeButton, viewModel.proposableProperty());
    }

    private void setNodeVisibility(Node node, ObservableValue<? extends Boolean> visible) {
        node.visibleProperty().bind(visible);
        node.managedProperty().bind(visible);
    }

    private void setViewProperties() {

    }

    private void setCustomFactories() {

    }

    private void createFields(Set<OfferField> offerFields) {
        int row = 0;
        for (OfferField field : offerFields) {
            TextField txtField = createField(field, row++);
            this.offerFields.put(field, txtField);
        }
    }

    private TextField createField(OfferField field, int row) {
        Label label = new Label(field.getName());

        TextField txtField = new TextField();
        txtField.setText(field.getContent());
        txtField.setEditable(false);

        fieldContainer.add(label, 0, row, 2, 1);
        fieldContainer.add(txtField, 2, row, GridPane.REMAINING, 1);

        return txtField;
    }

    private void removeFields() {
        offerFields.clear();
        fieldContainer.getChildren().clear();
    }
}
