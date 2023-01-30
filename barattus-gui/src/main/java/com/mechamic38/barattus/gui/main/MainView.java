package com.mechamic38.barattus.gui.main;

import com.mechamic38.barattus.core.user.UserRole;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.View;
import com.mechamic38.barattus.gui.common.ViewFactory;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.gui.login.LoginActivity;
import com.mechamic38.barattus.gui.util.FXMLUtils;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MainView extends BaseView implements Initializable {

    private final IMainViewModel viewModel;
    @FXML
    public HBox createOfferButton;
    @FXML
    public HBox offersButton;
    @FXML
    public HBox tradesButton;
    @FXML
    public HBox categoryButton;
    @FXML
    public HBox tradeParamsButton;
    @FXML
    protected GridPane graphic;
    private Parent currentScene;
    @FXML
    private VBox sideMenuBox;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button logoutButton;

    public MainView(IMainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onLogout() {
        removeCurrentScene();
        viewModel.logout();
        Platform.runLater(() -> new LoginActivity().show());
        getActivity().close();
    }

    @FXML
    private void onCategoryList() {
        changeContent(Views.CATEGORY_LIST);
    }

    @FXML
    private void onTradeParams() {
        changeContent(Views.TRADE_PARAMS);
    }

    @FXML
    private void onOffersList() {
        changeContent(Views.OFFER_LIST);
    }

    @FXML
    private void onCreateOffer() {
        changeContent(Views.CREATE_OFFER);
    }

    @FXML
    private void onTradesList() {
        changeContent(Views.TRADE_LIST);
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @Override
    public void changeContent(Views view) {
        removeCurrentScene();

        View contentView = FXMLUtils.loadViewFXML(
                ViewFactory.createView(view),
                view.fxml
        );
        contentView.setViewChangeAction(this::changeContent);

        currentScene = contentView.getGraphic();
        graphic.add(currentScene, 1, 0);
    }

    public void setViewChangeAction(Consumer<Views> viewChangeAction) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return viewModel.loggedUserProperty().get().getUsername();
        }, viewModel.loggedUserProperty()));

        BooleanBinding visibility = Bindings.createBooleanBinding(() -> {
            return viewModel.loggedUserRoleProperty().get().equals(UserRole.END_USER);
        }, viewModel.loggedUserRoleProperty());
        setVisibility(createOfferButton, visibility);
        setVisibility(tradesButton, visibility);
    }

    private void removeCurrentScene() {
        if (currentScene != null) {
            graphic.getChildren().remove(currentScene);
        }
    }

    private void setVisibility(Node node, BooleanBinding binding) {
        node.visibleProperty().bind(binding);
        node.managedProperty().bind(binding);
    }
}
