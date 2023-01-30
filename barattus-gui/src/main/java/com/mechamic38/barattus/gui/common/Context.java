package com.mechamic38.barattus.gui.common;

import javafx.scene.Scene;
import javafx.stage.Window;

/**
 * A Context is an abstract structure that allows to interact with a particular GUI environment.
 */
public interface Context {

    Scene getContextScene();

    Window getContextWindow();

    void focusRequest();

    void makeFocused();

    void close();
}
