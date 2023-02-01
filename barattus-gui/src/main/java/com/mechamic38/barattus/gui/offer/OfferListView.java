package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.CellFactoryProvider;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class OfferListView extends BaseView implements Initializable {

    private final IOfferListViewModel viewModel;
    private Consumer<Views> viewChangeAction;

    @FXML
    private ScrollPane graphic;

    @FXML
    private ComboBox<Category> rootCategoryBox;
    @FXML
    private ComboBox<Category> leafCategoryBox;

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
        viewModel.loadSubcategories(
                rootCategoryBox.getSelectionModel().getSelectedItem()
        );
    }

    @FXML
    private void onSearch() {
        if (ownOffersBox.isSelected()) {
            viewModel.loadOwnOffers(
                    leafCategoryBox.getSelectionModel().getSelectedItem()
            );
        } else {
            viewModel.loadOffers(
                    leafCategoryBox.getSelectionModel().getSelectedItem()
            );
        }
    }

    @FXML
    private void onViewOffer() {
        viewModel.setActiveOffer(
                offerTable.getSelectionModel().getSelectedItem()
        );
        this.changeContent(Views.OFFER_DETAILS);
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
        setAdminProperties();
        setViewProperties();
        setCustomFactories();

        viewModel.offersProperty().addListener((observable, oldValue, offers) -> {
            offerTable.itemsProperty().set(offers);
        });

        rootCategoryBox.itemsProperty().bind(viewModel.rootCategoriesProperty());
        leafCategoryBox.itemsProperty().bind(viewModel.leafCategoriesProperty());
    }

    private void setAdminProperties() {
    }

    private void setNodeVisibility(Node node, ObservableValue<? extends Boolean> visible) {
        node.visibleProperty().bind(visible);
        node.managedProperty().bind(visible);
    }

    private void setViewProperties() {
        leafCategoryBox.disableProperty().bind(
                rootCategoryBox.getSelectionModel().selectedItemProperty().isNull()
        );
        searchOffersButton.disableProperty().bind(Bindings.and(
                ownOffersBox.selectedProperty().not(),
                Bindings.or(
                        rootCategoryBox.getSelectionModel().selectedItemProperty().isNull(),
                        leafCategoryBox.getSelectionModel().selectedItemProperty().isNull()
                )
        ));
        viewOfferButton.disableProperty().bind(
                offerTable.getSelectionModel().selectedItemProperty().isNull()
        );
    }

    private void setCustomFactories() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        publishCol.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCreationDate().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
        ));
        statusCol.setCellValueFactory(cell -> new SimpleStringProperty(
                I18N.getValue(cell.getValue().getStatus().i18n)
        ));

        rootCategoryBox.setCellFactory(listView -> CellFactoryProvider.getCategoryBoxCell());
        rootCategoryBox.setButtonCell(CellFactoryProvider.getCategoryBoxCell());
        leafCategoryBox.setCellFactory(listView -> CellFactoryProvider.getCategoryBoxCell());
        leafCategoryBox.setButtonCell(CellFactoryProvider.getCategoryBoxCell());
    }
}
