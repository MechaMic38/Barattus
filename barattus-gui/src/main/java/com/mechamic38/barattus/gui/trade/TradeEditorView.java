package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
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
    private Consumer<Views> viewChangeAction;

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
    private void viewInitiatorOfferClicked() {
        /*controller.setInitiatorActive();
        this.changeContent(Views.OFFER_DETAILS);*/
    }

    @FXML
    private void viewProposedOfferClicked() {
        /*controller.setProposedActive();
        this.changeContent(Views.OFFER_DETAILS);*/
    }

    @FXML
    private void backClicked() {
        this.changeContent(Views.TRADE_LIST);
    }

    @FXML
    private void confirmTradeClicked() {
        /*Alert dialog;

        if (controller.confirmTrade(
                placeBox.getSelectionModel().getSelectedItem(),
                dayBox.getSelectionModel().getSelectedItem(),
                timeBox.getSelectionModel().getSelectedItem()
        )) {
            dialog = new Alert(
                    Alert.AlertType.INFORMATION,
                    presenter.getMessage(),
                    ButtonType.OK
            );
            dialog.showAndWait();
        } else {
            dialog = new Alert(
                    Alert.AlertType.WARNING,
                    presenter.getError(),
                    ButtonType.OK
            );
            dialog.showAndWait();
        }

        reload();*/
    }

    @FXML
    private void editClicked() {
        /*Alert dialog;

        if (controller.editTrade(
                placeBox.getSelectionModel().getSelectedItem(),
                dayBox.getSelectionModel().getSelectedItem(),
                timeBox.getSelectionModel().getSelectedItem()
        )) {
            dialog = new Alert(
                    Alert.AlertType.INFORMATION,
                    presenter.getMessage(),
                    ButtonType.OK
            );
            dialog.showAndWait();
        } else {
            dialog = new Alert(
                    Alert.AlertType.WARNING,
                    presenter.getError(),
                    ButtonType.OK
            );
            dialog.showAndWait();
        }

        reload();*/
    }

    @FXML
    private void rejectTradeClicked() {
        /*controller.rejectTrade();

        Alert dialog = new Alert(
                Alert.AlertType.INFORMATION,
                presenter.getMessage(),
                ButtonType.OK
        );
        dialog.showAndWait();

        reload();*/
    }

    @FXML
    private void acceptClicked() {
        /*controller.acceptTrade();

        Alert dialog = new Alert(
                Alert.AlertType.INFORMATION,
                presenter.getMessage(),
                ButtonType.OK
        );
        dialog.showAndWait();

        reload();*/
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

    private void setNodeVisibility(Node node, ObservableValue<? extends Boolean> visible) {
        node.visibleProperty().bind(visible);
        node.managedProperty().bind(visible);
    }
}
