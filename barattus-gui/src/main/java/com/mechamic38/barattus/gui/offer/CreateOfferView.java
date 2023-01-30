package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class CreateOfferView extends BaseView implements Initializable {

    private final ICreateOfferViewModel viewModel;
    private Consumer<Views> viewChangeAction;

    @FXML
    private ScrollPane graphic;
    @FXML
    private GridPane fieldContainer;

    @FXML
    private TextField titleField;

    @FXML
    private ComboBox<Category> rootCategoryBox;
    @FXML
    private ComboBox<Category> leafCategoryBox;

    @FXML
    private Button publishButton;

    public CreateOfferView(ICreateOfferViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onRootChange() {
        /*controller.updateLeafCategories(
                rootCategoryBox.getSelectionModel().getSelectedItem()
        );*/
    }

    @FXML
    private void onLeafChange() {
        /*removeFields();
        controller.updateCategoryFields(
                rootCategoryBox.getSelectionModel().getSelectedItem(),
                leafCategoryBox.getSelectionModel().getSelectedItem()
        );
        createFields();*/
    }

    @FXML
    private void onPublish() {
        /*Alert dialog;

        if (controller.publishOffer(
                titleField.getText().trim(),
                extractFields())
        ) {
            dialog = new Alert(
                    Alert.AlertType.INFORMATION,
                    presenter.getMessage(),
                    ButtonType.OK
            );
            dialog.showAndWait();
            this.changeScene(Scenes.OFFER_DETAILS);
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
