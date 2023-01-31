package com.mechamic38.barattus.core.category;

import java.util.Objects;

/**
 * Field of a {@link Category}.
 */
public class CategoryField {

    private final String name;
    private Type type;
    private boolean isMandatory;


    /**
     * Creates a new category field with the given name, type and "mandatoryness".
     *
     * @param name        Name of the field
     * @param type        Type of the field
     * @param isMandatory If it is mandatory
     */
    public CategoryField(String name, Type type, boolean isMandatory) {
        this.name = name;
        this.type = type;
        this.isMandatory = isMandatory;
    }

    public String getName() {
        return name;
    }

    public Type getFieldType() {
        return type;
    }

    public void setFieldType(Type type) {
        this.type = type;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryField that = (CategoryField) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    /**
     * Enum for the different {@link CategoryField} types.
     */
    public enum Type {
        STRING("field.type.string");

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