package com.mechamic38.barattus.core.offer;

import java.util.Objects;

/**
 * Field of a {@link Offer}.
 */
public class OfferField {

    private final String name;
    private final Type type;
    private String content;


    /**
     * Creates a new field with the given name, field type and isMandatory.
     *
     * @param name    Name of the field
     * @param type    Type of the field
     * @param content Content of the field
     */
    public OfferField(String name, Type type, String content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferField that = (OfferField) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    /**
     * Enum for the different {@link OfferField} types.
     */
    public enum Type {
        STRING("offer.field.type.string");

        private final String i18n;


        Type(String i18n) {
            this.i18n = i18n;
        }

        @Override
        public String toString() {
            //TODO: I18N Reference
            return super.toString();
        }
    }
}
