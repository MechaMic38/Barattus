package com.mechamic38.barattus.gui.trade;

import com.mechamic38.barattus.core.trade.Trade;
import com.mechamic38.barattus.core.trade.TradeStatus;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.CellFactoryProvider;
import com.mechamic38.barattus.gui.common.Views;
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
    private TableView<Trade> tradeTable;
    @FXML
    private TableColumn<Trade, String> initiatorOfferCol;
    @FXML
    private TableColumn<Trade, String> proposedOfferCol;
    @FXML
    private TableColumn<Trade, String> lastEditCol;

    public TradeListView(ITradeListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void viewTradeClicked() {
        viewModel.setActiveTrade(
                tradeTable.getSelectionModel().getSelectedItem()
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
    }

    private void setViewProperties() {
        viewTradeButton.disableProperty().bind(
                tradeTable.getSelectionModel().selectedItemProperty().isNull()
        );
    }

    private void setCustomFactories() {
        //TODO

        tradeStatusBox.setCellFactory(listView -> CellFactoryProvider.getTradeStatusBoxCell());
        tradeStatusBox.setButtonCell(CellFactoryProvider.getTradeStatusBoxCell());
    }
}
