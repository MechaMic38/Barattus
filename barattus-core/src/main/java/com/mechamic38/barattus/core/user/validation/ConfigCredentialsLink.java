package com.mechamic38.barattus.core.user.validation;

public class ConfigCredentialsLink extends LoginChainLink {

    private static final String CONFIG_USERNAME = "IngSoftware";
    private static final String CONFIG_PASSWORD = "Barattus2022";

    public ConfigCredentialsLink() {
    }

    @Override
    public String check(String username, String password) {
        if (CONFIG_USERNAME.equals(username) && CONFIG_PASSWORD.equals(password)) {
            return "configMatch";
        }
        return checkNext(username, password);
    }
}
