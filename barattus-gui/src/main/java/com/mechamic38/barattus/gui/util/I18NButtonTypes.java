package com.mechamic38.barattus.gui.util;

import com.mechamic38.barattus.i18n.api.I18N;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class I18NButtonTypes {

    public static ButtonType APPLY = createButtonType("dialog.button.apply", ButtonBar.ButtonData.APPLY);
    public static ButtonType OK = createButtonType("dialog.button.ok", ButtonBar.ButtonData.OK_DONE);
    public static ButtonType CANCEL = createButtonType("dialog.button.cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    public static ButtonType CLOSE = createButtonType("dialog.button.close", ButtonBar.ButtonData.CANCEL_CLOSE);
    public static ButtonType YES = createButtonType("dialog.button.yes", ButtonBar.ButtonData.YES);
    public static ButtonType NO = createButtonType("dialog.button.no", ButtonBar.ButtonData.NO);
    public static ButtonType FINISH = createButtonType("dialog.button.finish", ButtonBar.ButtonData.FINISH);
    public static ButtonType NEXT = createButtonType("dialog.button.next", ButtonBar.ButtonData.NEXT_FORWARD);
    public static ButtonType PREVIOUS = createButtonType("dialog.button.previous", ButtonBar.ButtonData.BACK_PREVIOUS);
    public static ButtonType RETRY = createButtonType("dialog.button.retry", ButtonBar.ButtonData.YES);

    private I18NButtonTypes() {

    }

    private static ButtonType createButtonType(String key, ButtonBar.ButtonData buttonData) {
        return new ButtonType(
                I18N.getValue(key),
                buttonData
        );
    }
}
