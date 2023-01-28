package com.mechamic38.barattus.i18n;

import com.mechamic38.barattus.i18n.api.LanguagePack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A {@link LanguagePack} that provides translation for the English language.
 */
public class EnglishLanguagePack extends LanguagePack {

    public EnglishLanguagePack() {
        super(Locale.ENGLISH);
    }

    @Override
    public @NotNull ResourceBundle getValues() {
        return getBundle(DEFAULT_RESOURCE_BUNDLE_NAME);
    }
}
