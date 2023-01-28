package com.mechamic38.barattus.gui.base;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.model.WorkbenchDialog;
import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.mechamic38.barattus.gui.api.Context;
import com.mechamic38.barattus.gui.api.ContextDialog;
import com.mechamic38.barattus.gui.util.I18NButtonTypes;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A {@link BaseView} wraps a UI element and adds extra functionality (like showing dialogs, overlays etc...)
 * to it by implementing the {@link Context} interface.
 *
 * @see Context
 */
public class BaseView extends GridPane implements Context {

    private final Workbench workbench;
    private final ObjectProperty<Node> content = new SimpleObjectProperty<>();


    public BaseView() {
        this.workbench = buildWorkbench();
        this.getChildren().add(workbench);
    }

    public BaseView(@Nullable Node content) {
        this();
        this.setContent(content);
    }

    private Workbench buildWorkbench() {
        return Workbench.builder(
                new WorkbenchModule(null, (Image) null) {
                    @Override
                    public Node activate() {
                        return content.get();
                    }
                }
        ).build();
    }

    public Node getContent() {
        return content.get();
    }

    public ObjectProperty<Node> contentProperty() {
        return content;
    }

    public void setContent(Node content) {
        this.content.set(content);
    }

    @Override
    public @NotNull ObservableList<Region> getBlockingOverlaysShown() {
        return workbench.getBlockingOverlaysShown();
    }

    @Override
    public @NotNull ObservableList<Region> getNonBlockingOverlaysShown() {
        return workbench.getNonBlockingOverlaysShown();
    }

    @Override
    public void showOverlay(Region region, boolean blocking) {
        workbench.showOverlay(region, blocking);
    }

    @Override
    public void hideOverlay(Region region) {
        workbench.hideOverlay(region);
    }

    @Override
    public ContextDialog showErrorDialog(String title, String message, Consumer<ButtonType> onResult) {
        return showErrorDialog(title, message, null, onResult);
    }

    @Override
    public ContextDialog showErrorDialog(String title, String message, Exception cause, Consumer<ButtonType> onResult) {
        return new WorkbenchContextDialog(
                workbench.showDialog(buildErrorDialog(title, message, cause, onResult)),
                ContextDialog.Type.ERROR
        );
    }

    @Override
    public ContextDialog showInformationDialog(String title, String message, Consumer<ButtonType> onResult) {
        return new WorkbenchContextDialog(
                workbench.showDialog(buildInformationDialog(title, message, onResult)),
                ContextDialog.Type.INFORMATION
        );
    }

    @Override
    public ContextDialog showConfirmationDialog(String title, String message, Consumer<ButtonType> onResult) {
        return new WorkbenchContextDialog(
                workbench.showDialog(buildConfirmationDialog(title, message, onResult)),
                ContextDialog.Type.CONFIRMATION
        );
    }

    @Override
    public ContextDialog showDialog(String title, Node content, Consumer<ButtonType> onResult, ButtonType... buttonTypes) {
        return new WorkbenchContextDialog(
                workbench.showDialog(
                        WorkbenchDialog.builder(title, content, buttonTypes).onResult(onResult).build()
                ),
                ContextDialog.Type.INFORMATION
        );
    }

    @Override
    public ButtonType showErrorDialogAndWait(String title, String message) {
        final var key = new Object();
        showErrorDialog(title, message, buttonType -> Platform.exitNestedEventLoop(key, buttonType));
        return (ButtonType) Platform.enterNestedEventLoop(key);
    }

    @Override
    public ButtonType showErrorDialogAndWait(String title, String message, Exception e) {
        final var key = new Object();
        showErrorDialog(title, message, e, buttonType -> Platform.exitNestedEventLoop(key, buttonType));
        return (ButtonType) Platform.enterNestedEventLoop(key);
    }

    @Override
    public ButtonType showInformationDialogAndWait(String title, String message) {
        final var key = new Object();
        showInformationDialog(title, message, buttonType -> Platform.exitNestedEventLoop(key, buttonType));
        return (ButtonType) Platform.enterNestedEventLoop(key);
    }

    @Override
    public ButtonType showConfirmationDialogAndWait(String title, String message) {
        final var key = new Object();
        showConfirmationDialog(title, message, buttonType -> Platform.exitNestedEventLoop(key, buttonType));
        return (ButtonType) Platform.enterNestedEventLoop(key);
    }

    @Override
    public ButtonType showDialogAndWait(String title, Node content, ButtonType... buttonTypes) {
        final var key = new Object();
        this.showDialog(title, content, buttonType -> Platform.exitNestedEventLoop(key, buttonType), buttonTypes);
        return (ButtonType) Platform.enterNestedEventLoop(key);
    }

    @Override
    public Scene getContextScene() {
        return workbench.getScene();
    }

    @Override
    public Window getContextWindow() {
        return workbench.getScene().getWindow();
    }

    @Override
    public void focusRequest() {
        final Window contextWindow = getContextWindow();
        if (contextWindow != null)
            contextWindow.requestFocus();
    }

    @Override
    public void toFrontRequest() {
        Window contextWindow = getContextWindow();
        if (contextWindow instanceof Stage stage) {
            stage.setIconified(false);
            stage.toFront();
        }
    }

    @Override
    public Boolean isShowing() {
        final Window contextWindow = getContextWindow();
        return contextWindow != null && contextWindow.isShowing();
    }

    @Override
    public void close() {
        var window = getContextWindow();
        if (window instanceof Stage) ((Stage) window).close();
        else window.hide();
    }

    private static WorkbenchDialog buildErrorDialog(@NotNull String title,
                                          @NotNull String message,
                                          @Nullable Exception exception,
                                          @NotNull Consumer<ButtonType> onResult) {
        return WorkbenchDialog.builder(title, message, I18NButtonTypes.OK)
                .onResult(onResult)
                .build();

    }

    private static WorkbenchDialog buildInformationDialog(@NotNull String title,
                                                @NotNull String message,
                                                @NotNull Consumer<ButtonType> onResult) {
        return WorkbenchDialog.builder(title, message, I18NButtonTypes.OK)
                .onResult(onResult)
                .build();
    }

    private static WorkbenchDialog buildConfirmationDialog(@NotNull String title,
                                                 @NotNull String message,
                                                 @NotNull Consumer<ButtonType> onResult) {
        return WorkbenchDialog.builder(title, message, I18NButtonTypes.NO, I18NButtonTypes.YES)
                .onResult(onResult)
                .build();
    }


    /**
     * Wraps an {@link WorkbenchDialog} into a {@link ContextDialog} implementation.
     */
    private static class WorkbenchContextDialog implements ContextDialog {

        private final WorkbenchDialog workbenchDialog;
        private final Type type;

        public WorkbenchContextDialog(@NotNull WorkbenchDialog workbenchDialog, @Nullable Type type) {
            this.workbenchDialog = Objects.requireNonNull(workbenchDialog);
            this.type = workbenchDialog.getType() != null ? Type.valueOf(workbenchDialog.getType().toString()) : type;
        }

        public WorkbenchContextDialog(@NotNull WorkbenchDialog workbenchDialog) {
            this(workbenchDialog, null);
        }

        @Override
        public ObjectProperty<EventHandler<Event>> onShownProperty() {
            return workbenchDialog.onShownProperty();
        }

        @Override
        public void setOnShown(EventHandler<Event> value) {
            workbenchDialog.setOnShown(value);
        }

        @Override
        public EventHandler<Event> getOnShown() {
            return workbenchDialog.getOnShown();
        }

        @Override
        public ObjectProperty<EventHandler<Event>> onHiddenProperty() {
            return workbenchDialog.onHiddenProperty();
        }

        @Override
        public void setOnHidden(EventHandler<Event> value) {
            workbenchDialog.setOnHidden(value);
        }

        @Override
        public EventHandler<Event> getOnHidden() {
            return workbenchDialog.getOnHidden();
        }

        @Override
        public Type getType() {
            return type;
        }

        @Override
        public ObservableList<ButtonType> getButtonTypes() {
            return workbenchDialog.getButtonTypes();
        }

        @Override
        public BooleanProperty maximizedProperty() {
            return workbenchDialog.maximizedProperty();
        }

        @Override
        public void setMaximized(boolean max) {
            workbenchDialog.setMaximized(max);
        }

        @Override
        public boolean isMaximized() {
            return workbenchDialog.isMaximized();
        }

        @Override
        public ObjectProperty<Node> contentProperty() {
            return workbenchDialog.contentProperty();
        }

        @Override
        public void setContent(Node content) {
            workbenchDialog.setContent(content);
        }

        @Override
        public Node getContent() {
            return workbenchDialog.getContent();
        }

        @Override
        public StringProperty titleProperty() {
            return workbenchDialog.titleProperty();
        }

        @Override
        public String getTitle() {
            return workbenchDialog.getTitle();
        }

        @Override
        public void setTitle(String title) {
            workbenchDialog.setTitle(title);
        }

        @Override
        public ObservableList<String> getStyleClass() {
            return workbenchDialog.getStyleClass();
        }

        @Override
        public ObjectProperty<Exception> exceptionProperty() {
            return workbenchDialog.exceptionProperty();
        }

        @Override
        public void setException(Exception ex) {
            workbenchDialog.setException(ex);
        }

        @Override
        public Exception getException() {
            return workbenchDialog.getException();
        }

        @Override
        public String getDetails() {
            return workbenchDialog.getDetails();
        }

        @Override
        public StringProperty detailsProperty() {
            return workbenchDialog.detailsProperty();
        }

        @Override
        public void setDetails(String details) {
            workbenchDialog.setDetails(details);
        }

        @Override
        public BooleanProperty blockingProperty() {
            return workbenchDialog.blockingProperty();
        }

        @Override
        public void setBlocking(boolean blocking) {
            workbenchDialog.setBlocking(blocking);
        }

        @Override
        public boolean isBlocking() {
            return workbenchDialog.isBlocking();
        }

        @Override
        public Consumer<ButtonType> getOnResult() {
            return workbenchDialog.getOnResult();
        }

        @Override
        public ObjectProperty<Consumer<ButtonType>> onResultProperty() {
            return workbenchDialog.onResultProperty();
        }

        @Override
        public void setOnResult(Consumer<ButtonType> onResult) {
            workbenchDialog.setOnResult(onResult);
        }

        @Override
        public ReadOnlyBooleanProperty showingProperty() {
            return workbenchDialog.showingProperty();
        }

        @Override
        public boolean isShowing() {
            return workbenchDialog.isShowing();
        }
    }
}
