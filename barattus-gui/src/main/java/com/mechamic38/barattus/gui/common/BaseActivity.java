package com.mechamic38.barattus.gui.common;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.WorkbenchSkin;
import com.dlsc.workbenchfx.model.WorkbenchDialog;
import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.dlsc.workbenchfx.view.WorkbenchView;
import com.mechamic38.barattus.gui.util.I18NButtonTypes;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Consumer;

abstract public class BaseActivity extends Stage implements Activity {

    protected final ObjectProperty<Node> content = new SimpleObjectProperty<>();
    protected boolean exitDialog = false;
    protected View view;
    protected Workbench workbench;

    protected BaseActivity() {
        super();
        buildExitDialogEvent();
        this.workbench = buildWorkbench();
        this.setScene(new Scene(new BorderPane(workbench)));
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

    abstract protected void onCreate();

    protected Node getContent() {
        return content.get();
    }

    protected void setContent(Node content) {
        this.content.set(content);
    }

    protected ObjectProperty<Node> contentProperty() {
        return content;
    }

    private Workbench buildWorkbench() {
        return initWorkbench(
                Workbench.builder(
                        new WorkbenchModule(null, (Image) null) {
                            @Override
                            public Node activate() {
                                return content.get();
                            }
                        }
                ).build()
        );
    }

    private Workbench initWorkbench(Workbench workbench) {
        workbench.skinProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Skin<?>> observable, Skin<?> oldValue, Skin<?> skin) {
                if (skin != null) {
                    try {
                        final var workbenchSkin = (WorkbenchSkin) skin;
                        Class<? extends WorkbenchSkin> workbenchSkinClass = workbenchSkin.getClass();
                        Field workbenchViewField = workbenchSkinClass.getDeclaredField("workbenchView");
                        workbenchViewField.setAccessible(true);
                    } catch (RuntimeException | ReflectiveOperationException ignored) {

                    }
                    observable.removeListener(this);
                }
            }
        });
        return workbench;
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
    public View getView() {
        return view;
    }

    @Override
    public void setView(View view) {
        this.view = view;
        this.setContent(view.getGraphic());
    }

    @Override
    public @NotNull ContextDialog showErrorDialog(String title, String message, Consumer<ButtonType> onResult) {
        return showErrorDialog(title, message, null, onResult);
    }

    @Override
    public @NotNull ContextDialog showErrorDialog(String title, String message, Exception cause, Consumer<ButtonType> onResult) {
        return new WorkbenchContextDialog(
                workbench.showDialog(buildErrorDialog(title, message, cause, onResult)),
                ContextDialog.Type.ERROR
        );
    }

    @Override
    public @NotNull ContextDialog showInformationDialog(String title, String message, Consumer<ButtonType> onResult) {
        return new WorkbenchContextDialog(
                workbench.showDialog(buildInformationDialog(title, message, onResult)),
                ContextDialog.Type.INFORMATION
        );
    }

    @Override
    public @NotNull ContextDialog showConfirmationDialog(String title, String message, Consumer<ButtonType> onResult) {
        return new WorkbenchContextDialog(
                workbench.showDialog(buildConfirmationDialog(title, message, onResult)),
                ContextDialog.Type.CONFIRMATION
        );
    }

    @Override
    public @NotNull ContextDialog showDialog(String title, Node content, Consumer<ButtonType> onResult, ButtonType... buttonTypes) {
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
        public EventHandler<Event> getOnShown() {
            return workbenchDialog.getOnShown();
        }

        @Override
        public void setOnShown(EventHandler<Event> value) {
            workbenchDialog.setOnShown(value);
        }

        @Override
        public ObjectProperty<EventHandler<Event>> onHiddenProperty() {
            return workbenchDialog.onHiddenProperty();
        }

        @Override
        public EventHandler<Event> getOnHidden() {
            return workbenchDialog.getOnHidden();
        }

        @Override
        public void setOnHidden(EventHandler<Event> value) {
            workbenchDialog.setOnHidden(value);
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
        public boolean isMaximized() {
            return workbenchDialog.isMaximized();
        }

        @Override
        public void setMaximized(boolean max) {
            workbenchDialog.setMaximized(max);
        }

        @Override
        public ObjectProperty<Node> contentProperty() {
            return workbenchDialog.contentProperty();
        }

        @Override
        public Node getContent() {
            return workbenchDialog.getContent();
        }

        @Override
        public void setContent(Node content) {
            workbenchDialog.setContent(content);
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
        public Exception getException() {
            return workbenchDialog.getException();
        }

        @Override
        public void setException(Exception ex) {
            workbenchDialog.setException(ex);
        }

        @Override
        public String getDetails() {
            return workbenchDialog.getDetails();
        }

        @Override
        public void setDetails(String details) {
            workbenchDialog.setDetails(details);
        }

        @Override
        public StringProperty detailsProperty() {
            return workbenchDialog.detailsProperty();
        }

        @Override
        public BooleanProperty blockingProperty() {
            return workbenchDialog.blockingProperty();
        }

        @Override
        public boolean isBlocking() {
            return workbenchDialog.isBlocking();
        }

        @Override
        public void setBlocking(boolean blocking) {
            workbenchDialog.setBlocking(blocking);
        }

        @Override
        public Consumer<ButtonType> getOnResult() {
            return workbenchDialog.getOnResult();
        }

        @Override
        public void setOnResult(Consumer<ButtonType> onResult) {
            workbenchDialog.setOnResult(onResult);
        }

        @Override
        public ObjectProperty<Consumer<ButtonType>> onResultProperty() {
            return workbenchDialog.onResultProperty();
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

    /**
     * Handler for application closing requests.
     */
    private class WindowCloseRequestHandler implements EventHandler<WindowEvent> {

        private boolean dialogShowing = false;

        @Override
        public void handle(WindowEvent event) {
            if (exitDialog) {
                makeFocused();

                if (dialogShowing) {
                    dialogShowing = true;
                    ButtonType buttonType = showConfirmationDialogAndWait(
                            I18N.getValue("window.close.dialog.title"),
                            I18N.getValue("window.close.dialog.msg")
                    );
                    dialogShowing = false;

                    if (buttonType.equals(ButtonType.NO)) {
                        event.consume();
                    }
                } else {
                    event.consume();
                }
            }
        }
    }
}
