package com.mechamic38.barattus.gui.common;

import javafx.scene.Parent;

import java.util.function.Consumer;

public interface View {

    Activity getActivity();

    void setActivity(Activity activity);

    Parent getGraphic();

    void changeContent(Views view);

    void setViewChangeAction(Consumer<Views> viewChangeAction);
}
