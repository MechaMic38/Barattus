package com.mechamic38.barattus.gui.common;

import javafx.fxml.FXML;
import javafx.scene.Parent;
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
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Parent getGraphic() {
        return graphic;
    }
}
