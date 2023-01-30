package com.mechamic38.barattus.gui.main;

import com.mechamic38.barattus.gui.common.BaseActivity;
import com.mechamic38.barattus.gui.common.Views;

public class MainActivity extends BaseActivity {

    public MainActivity() {
        super();
        onCreate();
    }

    @Override
    protected void onCreate() {
        setView(Views.MAIN);
    }
}
