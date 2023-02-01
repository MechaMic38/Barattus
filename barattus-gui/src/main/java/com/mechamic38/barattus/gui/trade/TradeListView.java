package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.trade.TradeStatus;
import com.mechamic38.barattus.core.usecase.TradeData;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.gui.util.CellFactoryProvider;
import com.mechamic38.barattus.gui.util.GUIUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class TradeListView extends BaseView implements Initializable {

    private final ITradeListViewModel viewModel;
    private Consumer<Views> viewChangeAction;

    @FXML
    private ScrollPane graphic;

    @FXML
    private Button viewTradeButton;

    @FXML
    private ComboBox<TradeStatus> tradeStatusBox;

    @FXML
    private TableView<TradeData> tradeTable;
    @FXML
    private TableColumn<TradeData, String> initiatorOfferCol;
    @FXML
    private TableColumn<TradeData, String> proposedOfferCol;
    @FXML
    private TableColumn<TradeData, String> lastEditCol;

    public TradeListView(ITradeListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void viewTradeClicked() {
        viewModel.setActiveTrade(
                tradeTable.getSelectionModel().getSelectedItem().getTrade()
        );
        this.changeContent(Views.TRADE_EDITOR);
    }

    @FXML
    private void tradeStatusSwitch() {
        viewModel.loadTrades(
                tradeStatusBox.getSelectionModel().getSelectedItem()
        );
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
    public void onViewCreated() {
        viewModel.initialize();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setViewProperties();
        setCustomFactories();

        tradeStatusBox.itemsProperty().set(FXCollections.observableList(
                Arrays.stream(TradeStatus.values()).toList()
        ));

        tradeTable.itemsProperty().bind(viewModel.tradesProperty());
    }

    private void setViewProperties() {
        viewTradeButton.disableProperty().bind(
                tradeTable.getSelectionModel().selectedItemProperty().isNull()
        );
    }

    private void setCustomFactories() {
        tradeStatusBox.setCellFactory(listView -> CellFactoryProvider.getTradeStatusBoxCell());
        tradeStatusBox.setButtonCell(CellFactoryProvider.getTradeStatusBoxCell());

        initiatorOfferCol.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getInitiatorOfferData().getOffer().getTitle()
        ));
        proposedOfferCol.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getProposedOfferData().getOffer().getTitle()
        ));
        lastEditCol.setCellValueFactory(cell -> new SimpleStringProperty(
                GUIUtils.convertLocalDateTime(cell.getValue().getTrade().getLastUpdate())
        ));
    }
}
