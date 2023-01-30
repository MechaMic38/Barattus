package com.mechamic38.barattus.gui.offer;

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

public class OfferListView extends BaseView implements Initializable {

    private final IOfferListViewModel viewModel;
    private Consumer<Views> viewChangeAction;

    @FXML
    private ScrollPane graphic;

    @FXML
    private ComboBox<String> rootCategoryBox;
    @FXML
    private ComboBox<String> leafCategoryBox;

    @FXML
    private CheckBox ownOffersBox;

    @FXML
    private Button searchOffersButton;
    @FXML
    private Button viewOfferButton;

    @FXML
    private TableView<Offer> offerTable;
    @FXML
    private TableColumn<Offer, String> titleCol;
    @FXML
    private TableColumn<Offer, String> publishCol;
    @FXML
    private TableColumn<Offer, String> statusCol;

    public OfferListView(IOfferListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onHierarchyChange() {
        /*controller.updateLeafCategories(
                rootCategoryBox.getSelectionModel().getSelectedItem()
        );*/
    }

    @FXML
    private void onSearch() {
        /*controller.updateOffers(
                rootCategoryBox.getSelectionModel().getSelectedItem(),
                leafCategoryBox.getSelectionModel().getSelectedItem(),
                ownOffersBox.isSelected()
        );*/
    }

    @FXML
    private void onViewOffer() {
        /*controller.setActiveTradeOffer(
                offerTable.getSelectionModel().getSelectedItem()
        );
        this.changeContent(Views.OFFER_DETAILS);*/
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
