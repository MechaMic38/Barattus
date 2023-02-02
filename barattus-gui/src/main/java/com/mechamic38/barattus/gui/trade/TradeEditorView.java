package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.gui.util.CellFactoryProvider;
import com.mechamic38.barattus.gui.util.GUIUtils;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class TradeEditorView extends BaseView implements Initializable {

    private final ITradeEditorViewModel viewModel;

    @FXML
    private ScrollPane graphic;

    @FXML
    private TextField initiatorTitleField;
    @FXML
    private TextField initiatorUsernameField;
    @FXML
    private TextField initiatorCategoryField;
    @FXML
    private TextField proposedTitleField;
    @FXML
    private TextField proposedUsernameField;
    @FXML
    private TextField proposedCategoryField;
    @FXML
    private TextField lastEditField;

    @FXML
    private ComboBox<String> placeBox;
    @FXML
    private ComboBox<DayOfWeek> dayBox;
    @FXML
    private ComboBox<LocalTime> timeBox;

    @FXML
    private Button viewInitiatorOfferButton;
    @FXML
    private Button viewProposedOfferButton;
    @FXML
    private Button backButton;
    @FXML
    private Button rejectTradeButton;
    @FXML
    private Button confirmTradeButton;
    @FXML
    private Button editButton;
    @FXML
    private Button acceptButton;

    public TradeEditorView(ITradeEditorViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onViewInitiatorOffer() {
        viewModel.setInitiatorActive();
        this.changeContent(Views.OFFER_DETAILS);
    }

    @FXML
    private void onViewProposedOffer() {
        viewModel.setProposedActive();
        this.changeContent(Views.OFFER_DETAILS);
    }

    @FXML
    private void onBack() {
        this.changeContent(Views.TRADE_LIST);
    }

    @FXML
    private void onConfirmTrade() {
        if (viewModel.confirmTrade(
                placeBox.getSelectionModel().getSelectedItem(),
                dayBox.getSelectionModel().getSelectedItem(),
                timeBox.getSelectionModel().getSelectedItem()
        )) {
            getActivity().showInformationDialog(
                    I18N.getValue("trade.editor.title"),
                    I18N.getValue("trade.confirm.success"),
                    buttonType -> {
                    }
            );
        }
    }

    @FXML
    private void onEditTrade() {
        if (viewModel.editTrade(
                placeBox.getSelectionModel().getSelectedItem(),
                dayBox.getSelectionModel().getSelectedItem(),
                timeBox.getSelectionModel().getSelectedItem()
        )) {
            getActivity().showInformationDialog(
                    I18N.getValue("trade.editor.title"),
                    I18N.getValue("trade.edited.success"),
                    buttonType -> {
                    }
            );
        }
    }

    @FXML
    private void onRejectTrade() {
        if (viewModel.rejectTrade()) {
            getActivity().showInformationDialog(
                    I18N.getValue("trade.editor.title"),
                    I18N.getValue("trade.reject.success"),
                    buttonType -> {
                    }
            );
        }
    }

    @FXML
    private void onAcceptTrade() {
        if (viewModel.acceptTrade()) {
            getActivity().showInformationDialog(
                    I18N.getValue("trade.editor.title"),
                    I18N.getValue("trade.accept.success"),
                    buttonType -> {
                    }
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
        setTradeProperties();
        setViewProperties();
        setCustomFactories();

        placeBox.itemsProperty().bind(viewModel.placesProperty());
        dayBox.itemsProperty().bind(viewModel.daysProperty());
        timeBox.itemsProperty().bind(viewModel.timesProperty());

        viewModel.errorProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) return;
            getActivity().showErrorDialog(
                    I18N.getValue("trade.editor.title"),
                    I18N.getValue(newValue),
                    buttonType -> {
                        viewModel.errorProperty().set("");
                    }
            );
        });

        viewModel.tradeDataProperty().addListener((observable, oldValue, tradeData) -> {
            initiatorTitleField.setText(
                    tradeData.getInitiatorOfferData().getOffer().getTitle()
            );
            initiatorUsernameField.setText(
                    tradeData.getInitiatorOfferData().getOffer().getUserID()
            );
            initiatorCategoryField.setText(
                    GUIUtils.convertCategoryName(tradeData.getInitiatorOfferData().getCategory())
            );
            proposedTitleField.setText(
                    tradeData.getProposedOfferData().getOffer().getTitle()
            );
            proposedUsernameField.setText(
                    tradeData.getProposedOfferData().getOffer().getUserID()
            );
            proposedCategoryField.setText(
                    GUIUtils.convertCategoryName(tradeData.getProposedOfferData().getCategory())
            );
            lastEditField.setText(
                    GUIUtils.convertLocalDateTime(tradeData.getTrade().getLastUpdate())
            );

            String place = tradeData.getTrade().getTradeDetails().getPlace();
            if (place != null && !place.isBlank()) placeBox.setValue(place);

            DayOfWeek day = tradeData.getTrade().getTradeDetails().getDay();
            if (day != null) dayBox.setValue(day);

            LocalTime time = tradeData.getTrade().getTradeDetails().getTime();
            if (time != null) timeBox.setValue(time);
        });
    }

    private void setTradeProperties() {
        BooleanProperty editTurn = viewModel.editTurnProperty();
        BooleanProperty unconfirmed = viewModel.unconfirmedProperty();
        BooleanProperty ongoing = viewModel.ongoingProperty();

        confirmTradeButton.disableProperty().bind(Bindings.or(
                Bindings.or(
                        editTurn.not(),
                        placeBox.getSelectionModel().selectedItemProperty().isNull()
                ),
                Bindings.or(
                        dayBox.getSelectionModel().selectedItemProperty().isNull(),
                        timeBox.getSelectionModel().selectedItemProperty().isNull()
                )
        ));
        rejectTradeButton.disableProperty().bind(editTurn.not());
        editButton.disableProperty().bind(editTurn.not());
        acceptButton.disableProperty().bind(editTurn.not());

        setNodeVisibility(rejectTradeButton, unconfirmed);
        setNodeVisibility(confirmTradeButton, unconfirmed);

        setNodeVisibility(editButton, ongoing);
        setNodeVisibility(acceptButton, ongoing);
    }

    private void setViewProperties() {

    }

    private void setCustomFactories() {
        dayBox.setCellFactory(listView -> CellFactoryProvider.getDayBoxCell());
        dayBox.setButtonCell(CellFactoryProvider.getDayBoxCell());
        timeBox.setCellFactory(listView -> CellFactoryProvider.getHourBoxCell());
        timeBox.setButtonCell(CellFactoryProvider.getHourBoxCell());
    }

    private void setNodeVisibility(Node node, ObservableValue<? extends Boolean> visible) {
        node.visibleProperty().bind(visible);
        node.managedProperty().bind(visible);
    }
}
