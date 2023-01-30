package com.mechamic38.barattus.gui.common;

import com.mechamic38.barattus.core.user.User;

public class SessionState {

    private static SessionState INSTANCE;

    private boolean adminMode;
    private User user;

    private SessionState() {
    }

    public static SessionState getInstance() {
        if (INSTANCE == null) {
            synchronized (SessionState.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SessionState();
                }
            }
        }
        return INSTANCE;
    }

    public boolean isAdminMode() {
        return adminMode;
    }

    public void setAdminMode(boolean adminMode) {
        this.adminMode = adminMode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
