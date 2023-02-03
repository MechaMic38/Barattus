package com.mechamic38.barattus.gui.tradeparams;

import com.mechamic38.barattus.core.tradeparams.HourInterval;
import com.mechamic38.barattus.gui.common.BaseView;
import com.mechamic38.barattus.gui.util.CellFactoryProvider;
import com.mechamic38.barattus.gui.util.I18NButtonTypes;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.ResourceBundle;

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
    private Spinner<Integer> startHourSpinner;
    @FXML
    private Spinner<Integer> startMinuteSpinner;
    @FXML
    private Spinner<Integer> endHourSpinner;
    @FXML
    private Spinner<Integer> endMinuteSpinner;

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
        if (viewModel.saveChanges(
                squareField.getText().trim(),
                expirationDaysField.getText().trim()
        )) {
            getActivity().showInformationDialog(
                    I18N.getValue("trade.params.title"),
                    I18N.getValue("trade.params.save.success"),
                    buttonType -> {
                    }
            );
        }
        ;
    }

    @FXML
    private void onLoad() {
        getActivity().showWarningDialog(
                I18N.getValue("trade.params.title"),
                I18N.getValue("trade.params.warning.load"),
                buttonType -> {
                    if (buttonType.equals(I18NButtonTypes.YES)) {
                        FileChooser fc = new FileChooser();
                        fc.getExtensionFilters().add(
                                new FileChooser.ExtensionFilter("Json Files", "*.json")
                        );

                        File selectedFile = fc.showOpenDialog(getActivity().getContextWindow());
                        if (selectedFile == null) return;

                        if (viewModel.loadFromFile(selectedFile.getPath())) {
                            getActivity().showInformationDialog(
                                    I18N.getValue("trade.params.title"),
                                    I18N.getValue("trade.params.load.success"),
                                    ignored -> {
                                    }
                            );
                        }
                    }
                }
        );
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
                startHourSpinner.getValue(),
                startMinuteSpinner.getValue(),
                endHourSpinner.getValue(),
                endMinuteSpinner.getValue()
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
    public void onViewCreated() {
        viewModel.initialize();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAdminProperties();
        setViewProperties();
        setCustomFactories();

        viewModel.errorProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isBlank()) return;
            getActivity().showErrorDialog(
                    I18N.getValue("trade.params.title"),
                    I18N.getValue(newValue),
                    buttonType -> {
                        viewModel.errorProperty().set("");
                    }
            );
        });

        viewModel.tradeParamsProperty().addListener((observable, oldValue, params) -> {
            squareField.setText(params.getSquare());
            expirationDaysField.setText(Integer.toString(params.getExpirationDays()));
        });

        placeListView.itemsProperty().bind(viewModel.placesProperty());
        dayListView.itemsProperty().bind(viewModel.daysProperty());
        intervalListView.itemsProperty().bind(viewModel.intervalsProperty());
    }

    private void setAdminProperties() {
        ReadOnlyBooleanProperty admin = viewModel.adminProperty();

        squareField.editableProperty().bind(admin);
        expirationDaysField.editableProperty().bind(admin);

        setNodeVisibility(loadFromFileButton, admin);
        setNodeVisibility(placeField, admin);
        setNodeVisibility(addIntervalButton, admin);
        setNodeVisibility(removeIntervalButton, admin);
        setNodeVisibility(addDayButton, admin);
        setNodeVisibility(removeDayButton, admin);
        setNodeVisibility(addPlaceButton, admin);
        setNodeVisibility(removePlaceButton, admin);
        setNodeVisibility(applyButton, admin);
        setNodeVisibility(dayBox, admin);
        setNodeVisibility(startHourSpinner, admin);
        setNodeVisibility(startMinuteSpinner, admin);
        setNodeVisibility(endHourSpinner, admin);
        setNodeVisibility(endMinuteSpinner, admin);
        setNodeVisibility(startIntervalLabel, admin);
        setNodeVisibility(endIntervalLabel, admin);
    }

    private void setNodeVisibility(Node node, ReadOnlyBooleanProperty visible) {
        node.visibleProperty().bind(visible);
        node.managedProperty().bind(visible);
    }

    private void setViewProperties() {
        applyButton.disableProperty().bind(Bindings.or(
                squareField.textProperty().isEmpty(),
                expirationDaysField.textProperty().isEmpty()
        ));
        addPlaceButton.disableProperty().bind(
                placeField.textProperty().isEmpty()
        );
        removePlaceButton.disableProperty().bind(
                placeListView.getSelectionModel().selectedItemProperty().isNull()
        );
        addDayButton.disableProperty().bind(
                dayBox.getSelectionModel().selectedItemProperty().isNull()
        );
        removeDayButton.disableProperty().bind(
                dayListView.getSelectionModel().selectedItemProperty().isNull()
        );
        removeIntervalButton.disableProperty().bind(
                intervalListView.getSelectionModel().selectedItemProperty().isNull()
        );
        addIntervalButton.disableProperty().bind(Bindings.or(
                Bindings.or(
                        startHourSpinner.valueProperty().isNull(),
                        startMinuteSpinner.valueProperty().isNull()
                ),
                Bindings.or(
                        endHourSpinner.valueProperty().isNull(),
                        endMinuteSpinner.valueProperty().isNull()
                )
        ));

        dayBox.itemsProperty().set(FXCollections.observableList(Arrays.stream(DayOfWeek.values()).toList()));
    }

    private void setCustomFactories() {
        startHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
        endHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
        startMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
        endMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));

        dayBox.setCellFactory(listView -> CellFactoryProvider.getDayBoxCell());
        dayBox.setButtonCell(CellFactoryProvider.getDayBoxCell());
        dayListView.setCellFactory(listView -> CellFactoryProvider.getDayListCell());
        intervalListView.setCellFactory(listView -> CellFactoryProvider.getIntervalListCell());
    }
}
