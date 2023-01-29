package com.mechamic38.barattus.gui.common;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * A {@link BaseView} wraps a UI element and adds extra functionality (like showing dialogs, overlays etc...)
 * to it by implementing the {@link Context} interface.
 *
 * @see Context
 */
abstract public class BaseView implements View {

    protected Activity activity;
    @FXML
    protected GridPane graphic;

    public BaseView() {

    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    @Override
    public Node getGraphic() {
        return graphic;
    }
}
