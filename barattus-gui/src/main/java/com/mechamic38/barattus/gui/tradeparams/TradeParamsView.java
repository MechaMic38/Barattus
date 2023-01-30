package com.mechamic38.barattus.gui.tradeparams;

import com.mechamic38.barattus.core.tradeparams.HourInterval;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.common.Views;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class TradeParamsView extends BaseView implements Initializable {

    private final ITradeParamsViewModel viewModel;

    @FXML
    private ScrollPane graphic;
    @FXML
    private Label startIntervalLabel;
    @FXML
    private Label endIntervalLabel;

    @FXML
    private TextField squareField;
    @FXML
    private TextField expirationDaysField;
    @FXML
    private TextField placeField;

    @FXML
    private Button applyButton;
    @FXML
    private Button loadFromFileButton;
    @FXML
    private Button addPlaceButton;
    @FXML
    private Button removePlaceButton;
    @FXML
    private Button addDayButton;
    @FXML
    private Button removeDayButton;
    @FXML
    private Button removeIntervalButton;
    @FXML
    private Button addIntervalButton;

    @FXML
    private ComboBox<DayOfWeek> dayBox;
    @FXML
    private ComboBox<Integer> startHourBox;
    @FXML
    private ComboBox<Integer> startMinuteBox;
    @FXML
    private ComboBox<Integer> endHourBox;
    @FXML
    private ComboBox<Integer> endMinuteBox;

    @FXML
    private ListView<String> placeListView;
    @FXML
    private ListView<DayOfWeek> dayListView;
    @FXML
    private ListView<HourInterval> intervalListView;

    public TradeParamsView(ITradeParamsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void onApply() {
        viewModel.saveChanges();
        getActivity().showInformationDialog(
                "Save",
                I18N.getValue("trade.params.save.success"),
                buttonType -> {
                }
        );
    }

    @FXML
    private void onLoad() {
        /*Alert dialog;
        dialog = new Alert(
                Alert.AlertType.WARNING,
                presenter.getMessage("loadFromFileWarning"),
                ButtonType.YES,
                ButtonType.NO
        );
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.NO) return;

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Json Files", "*.json")
        );

        File selectedFile = fc.showOpenDialog(windowHandler.getStage());
        if (selectedFile == null) return;

        boolean success = viewModel.loadFromFile(selectedFile.getPath());
        if (success) {
            dialog = new Alert(
                    Alert.AlertType.INFORMATION,
                    presenter.getMessage(),
                    ButtonType.OK
            );
            dialog.showAndWait();
        } else {
            dialog = new Alert(
                    Alert.AlertType.ERROR,
                    presenter.getError(),
                    ButtonType.OK
            );
            dialog.showAndWait();
        }

        reload();*/
    }

    @FXML
    private void onAddPlace() {
        viewModel.addPlace(placeField.getText());
    }

    @FXML
    private void onRemovePlace() {
        viewModel.removePlace(
                placeListView.getSelectionModel().getSelectedItem()
        );
    }

    @FXML
    private void onAddDay() {
        viewModel.addDay(
                dayBox.getSelectionModel().getSelectedItem()
        );
    }

    @FXML
    private void onRemoveDay() {
        viewModel.removeDay(
                dayListView.getSelectionModel().getSelectedItem()
        );
    }

    @FXML
    private void onAddInterval() {
        viewModel.addInterval(
                startHourBox.getSelectionModel().getSelectedItem(),
                startMinuteBox.getSelectionModel().getSelectedItem(),
                endHourBox.getSelectionModel().getSelectedItem(),
                endMinuteBox.getSelectionModel().getSelectedItem()
        );
    }

    @FXML
    private void onRemoveInterval() {
        viewModel.removeInterval(
                intervalListView.getSelectionModel().getSelectedItem()
        );
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }

    @Override
    public void changeContent(Views view) {

    }

    @Override
    public void setViewChangeAction(Consumer<Views> viewChangeAction) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
}
