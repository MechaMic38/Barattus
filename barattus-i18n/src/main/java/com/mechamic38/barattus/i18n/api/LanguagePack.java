package com.mechamic38.barattus.i18n.api;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Provides the {@link ResourceBundle} that defines a language.
 */
abstract public class LanguagePack {

    protected static final String DEFAULT_RESOURCE_BUNDLE_NAME = "com.mechamic38.barattus.i18n.Values";

    private final Locale locale;


    protected LanguagePack(@NotNull Locale locale) {
        this.locale = Objects.requireNonNull(locale);
    }

    public final @NotNull Locale getLocale() {
        return locale;
    }

    /**
     * Gives the bundle of internationalized values.
     */
    @NotNull
    public abstract ResourceBundle getValues();

    /**
     * Gets a resource bundle using the specified base name and the locale
     * of the language pack.
     *
     * @see ResourceBundle#getBundle(String, Locale)
     */
    @SuppressWarnings("SameParameterValue")
    protected ResourceBundle getBundle(String baseName) {
        return ResourceBundle.getBundle(baseName, locale);
    }

}
