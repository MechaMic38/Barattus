package com.mechamic38.barattus.gui.util;

import com.mechamic38.barattus.gui.common.View;
import com.mechamic38.barattus.i18n.api.I18N;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class FXMLUtils {

    public static View loadViewFXML(View view, String viewName) {
        FXMLLoader loader = new FXMLLoader(
                FXMLUtils.class.getResource("/fxml/" + viewName + "View.fxml"),
                I18N.getValues()
        );

        loader.setControllerFactory(v -> view);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            //System.exit(-1); //if not all views can be found, stop execution
        }

        return loader.getController();
    }
}
