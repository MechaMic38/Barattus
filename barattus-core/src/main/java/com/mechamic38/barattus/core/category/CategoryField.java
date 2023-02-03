package com.mechamic38.barattus.core.category;

import java.util.Objects;

/**
 * Field of a {@link Category}.
 */
public class CategoryField {

    private final String name;
    private Type type;
    private boolean mandatory;


    /**
     * Creates a new category field with the given name, type and "mandatoryness".
     *
     * @param name      Name of the field
     * @param type      Type of the field
     * @param mandatory If it is mandatory
     */
    public CategoryField(String name, Type type, boolean mandatory) {
        this.name = name;
        this.type = type;
        this.mandatory = mandatory;
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

    public boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public CategoryField clone() {
        return new CategoryField(name, type, mandatory);
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
        STRING("category.field.type.string");

        public final String i18n;


        Type(String i18n) {
            this.i18n = i18n;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
