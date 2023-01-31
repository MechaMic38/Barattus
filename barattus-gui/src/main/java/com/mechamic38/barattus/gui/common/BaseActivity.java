package com.mechamic38.barattus.gui.common;

import com.mechamic38.barattus.gui.util.DialogBuilder;
import com.mechamic38.barattus.gui.util.FXMLUtils;
import com.mechamic38.barattus.gui.util.I18NButtonTypes;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

abstract public class BaseActivity extends Stage implements Activity {

    protected boolean exitDialog = false;
    protected View view;

    protected BaseActivity() {
        super();
        buildExitDialogEvent();
    }

    private static Alert buildErrorDialog(@NotNull String title,
                                          @NotNull String message) {
        return new DialogBuilder(
                title,
                message,
                Alert.AlertType.ERROR,
                I18NButtonTypes.OK
        ).build();
    }

    private static Alert buildWarningDialog(@NotNull String title,
                                            @NotNull String message) {
        return new DialogBuilder(
                title,
                message,
                Alert.AlertType.WARNING,
                I18NButtonTypes.NO, I18NButtonTypes.YES
        ).build();
    }

    private static Alert buildInformationDialog(@NotNull String title,
                                                @NotNull String message) {
        return new DialogBuilder(
                title,
                message,
                Alert.AlertType.INFORMATION,
                I18NButtonTypes.OK
        ).build();
    }

    private static Alert buildConfirmationDialog(@NotNull String title,
                                                 @NotNull String message) {
        return new DialogBuilder(
                title,
                message,
                Alert.AlertType.CONFIRMATION,
                I18NButtonTypes.NO, I18NButtonTypes.YES
        ).build();
    }

    abstract protected void onCreate();

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void setView(View view) {
        this.view = view;
        view.setActivity(this);
        this.setScene(new Scene(view.getGraphic()));
    }

    @Override
    public void setView(Views view) {
        View fxmlView = FXMLUtils.loadViewFXML(
                ViewFactory.createView(view),
                view.fxml
        );
        setTitle(view.title);
        setView(fxmlView);
    }

    @Override
    public void showErrorDialog(String title, String message, Consumer<ButtonType> onResult) {
        showErrorDialog(title, message, null, onResult);
    }

    @Override
    public void showErrorDialog(String title, String message, Exception cause, Consumer<ButtonType> onResult) {
        buildErrorDialog(title, message).showAndWait().ifPresent(onResult);
    }

    @Override
    public void showWarningDialog(String title, String message, Consumer<ButtonType> onResult) {
        buildWarningDialog(title, message).showAndWait().ifPresent(onResult);
    }

    @Override
    public void showInformationDialog(String title, String message, Consumer<ButtonType> onResult) {
        buildInformationDialog(title, message).showAndWait().ifPresent(onResult);
    }

    @Override
    public void showConfirmationDialog(String title, String message, Consumer<ButtonType> onResult) {
        buildConfirmationDialog(title, message).showAndWait().ifPresent(onResult);
    }

    @Override
    public Scene getContextScene() {
        return getScene();
    }

    @Override
    public Window getContextWindow() {
        return this;
    }

    @Override
    public void focusRequest() {
        requestFocus();
    }

    @Override
    public void makeFocused() {
        this.setIconified(false);
        this.toFront();
    }

    private void buildExitDialogEvent() {
        this.addEventFilter(
                WindowEvent.WINDOW_CLOSE_REQUEST,
                new WindowCloseRequestHandler()
        );
    }

    /**
     * Handler for application closing requests.
     */
    private class WindowCloseRequestHandler implements EventHandler<WindowEvent> {

        private boolean dialogShowing = false;

        @Override
        public void handle(WindowEvent event) {
            if (exitDialog) {
                makeFocused();

                if (!dialogShowing) {
                    dialogShowing = true;
                    showConfirmationDialog(
                            I18N.getValue("window.close.dialog.title"),
                            I18N.getValue("window.close.dialog.msg"),
                            buttonType -> {
                                if (buttonType.equals(ButtonType.NO)) {
                                    event.consume();
                                }
                            }
                    );
                    dialogShowing = false;
                } else {
                    event.consume();
                }
            }
        }
    }
}
