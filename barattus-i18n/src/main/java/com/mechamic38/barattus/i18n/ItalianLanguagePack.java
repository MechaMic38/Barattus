package com.mechamic38.barattus.i18n;

import com.mechamic38.barattus.i18n.api.LanguagePack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A {@link LanguagePack} that provides translation for the Italian language.
 */
public class ItalianLanguagePack extends LanguagePack {

    private static final Locale LOCALE = new Locale("it", "IT");


    public ItalianLanguagePack() {
        super(LOCALE);
    }

    @Override
    public @NotNull ResourceBundle getValues() {
        return getBundle(DEFAULT_RESOURCE_BUNDLE_NAME);
    }
}
