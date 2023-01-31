package com.mechamic38.barattus.i18n.api;

import com.mechamic38.barattus.i18n.EnglishLanguagePack;
import com.mechamic38.barattus.i18n.ItalianLanguagePack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.PropertyKey;

import java.text.MessageFormat;
import java.util.*;

public class I18N {

    /**
     * Stores all the available language packs
     */
    private static final Map<Locale, List<LanguagePack>> availableLanguagePacks = new LinkedHashMap<>();

    /**
     * The backing language-pack
     */
    private static volatile LanguagePack languagePack;


    static {
        loadPacks();

        // initializing the languagePack (for preventing null pointer exceptions)
        setLocale(Locale.getDefault());
    }

    private I18N() {
        // Not instantiable
    }

    /**
     * Loads the internal language packs.
     */
    public static void loadPacks() {
        availableLanguagePacks.putAll(getAvailableLanguagePacks());
    }

    private static Map<Locale, List<LanguagePack>> getAvailableLanguagePacks() {
        LinkedHashMap<Locale, List<LanguagePack>> languagePacks = new LinkedHashMap<>();
        languagePacks.put(Locale.ENGLISH, List.of(new EnglishLanguagePack()));
        languagePacks.put(Locale.ITALY, List.of(new ItalianLanguagePack()));

        return languagePacks;
    }

    /**
     * Recognizes the required {@link LanguagePack} for the default {@link Locale}.
     */
    private static LanguagePack recognizeLanguagePack() {
        Locale currentLocale = Locale.getDefault();

        Optional<LanguagePack> foundLanguagePack = getLanguagePackForLocale(currentLocale);
        return foundLanguagePack.orElseGet(EnglishLanguagePack::new);
    }

    /**
     * Maps the given locale to the language-pack.
     */
    private static Optional<LanguagePack> getLanguagePackForLocale(Locale locale) {
        return availableLanguagePacks.getOrDefault(locale, Collections.emptyList()).stream().findFirst();
    }

    /**
     * Gets the resource-bundle currently used for localization.
     */
    @NotNull
    public static ResourceBundle getValues() {
        return languagePack.getValues();
    }

    @NotNull
    public static Locale getLocale() {
        return languagePack.getLocale();
    }

    /**
     * Sets the default locale for this instance of the Java Virtual Machine (see {@link Locale#setDefault(Locale)}),
     * and searches for the right {@link LanguagePack} mapped to it. If no language-pack found for the given locale,
     * the English language pack is configured.
     *
     * @param locale the locale representing the language/area
     */
    public static synchronized void setLocale(@NotNull Locale locale) {
        Locale.setDefault(locale);
        languagePack = recognizeLanguagePack();
    }

    /**
     * Gets the internationalized value from the default resource bundle.
     *
     * @param key  the property-key
     * @param args only has a role if the property is a pattern-string (see {@link MessageFormat}).
     * @return the property-value
     */
    @NotNull
    public static String getValue(
            @PropertyKey(resourceBundle = "com.mechamic38.barattus.i18n.Values") String key,
            @Nullable Object... args
    ) {
        try {
            return getValue(getValues(), key, args);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    private static String getValue(@NotNull ResourceBundle resourceBundle, @NotNull String key, Object[] args) {
        if (args == null || args.length == 0) return resourceBundle.getString(key);
        return getFormat(resourceBundle, key, args);
    }

    private static String getFormat(@NotNull ResourceBundle resourceBundle, @NotNull String key, Object... args) {
        return MessageFormat.format(resourceBundle.getString(key), args);
    }
}
