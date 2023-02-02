package com.mechamic38.barattus.gui.common;

import javafx.scene.Parent;

import java.util.function.Consumer;

/**
 * A {@link BaseView} wraps a UI element and adds extra functionality (like showing dialogs, overlays etc...)
 * to it by implementing the {@link Context} interface.
 *
 * @see Context
 */
abstract public class BaseView implements View {

    protected Activity activity;
    protected Consumer<Views> viewChangeAction;

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
    abstract public Parent getGraphic();

    @Override
    public void changeContent(Views view) {
        if (viewChangeAction != null) viewChangeAction.accept(view);
    }

    @Override
    public void setViewChangeAction(Consumer<Views> viewChangeAction) {
        this.viewChangeAction = viewChangeAction;
    }
}
