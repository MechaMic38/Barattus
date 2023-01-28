package com.mechamic38.barattus.gui.window;

import com.mechamic38.barattus.gui.api.Context;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * A BaseWindow is a {@link Stage} implementation that supports
 * internationalized titles.
 * <p>
 * Also, it provides support for dialogs and on window close events.
 *
 * @param <C> the type of the content that is shown in the Window's scene.
 */
abstract public class BaseWindow<C extends Parent & Context> extends Stage {

    protected Context content;
    protected boolean exitDialog = false;


    /**
     * Creates a BaseWindow with an initial title.
     *
     * @param title the title
     */
    private BaseWindow(String title) {
        super();
        setTitle(title);
        buildExitDialogEvent();
    }

    /**
     * Creates a BaseWindow with an initial title and content.
     *
     * @param title   the title
     * @param content the graphic content
     */
    protected BaseWindow(String title, C content) {
        this(title);
        this.content = content;
    }

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

                if (dialogShowing) {
                    dialogShowing = true;
                    ButtonType buttonType = content.showConfirmationDialogAndWait(
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
