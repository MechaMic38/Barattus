package com.mechamic38.barattus.core.category;

import com.mechamic38.barattus.core.common.Entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Category extends Entity<String> {

    private final String hierarchyName;
    private final String name;
    private final List<CategoryField> nativeFields;
    private String parentName;
    private String description;


    /**
     * Creates a new category with the given name, description, hierarchy name
     * and parent name.
     *
     * @param name          Name of the category
     * @param description   Description of the category
     * @param hierarchyName Name of the hierarchy it belongs to
     * @param parentName    Name of the parent category
     */
    public Category(String hierarchyName,
                    String name,
                    String parentName,
                    String description) {
        this.id = combineIdKeys(hierarchyName, name);
        this.hierarchyName = hierarchyName;
        this.name = name;
        this.parentName = parentName;
        this.description = description;
        this.nativeFields = new LinkedList<>();
    }

    private Category(String id, String hierarchyName, String name,
                     String parentName, String description, List<CategoryField> nativeFields) {
        this.id = id;
        this.hierarchyName = hierarchyName;
        this.name = name;
        this.parentName = parentName;
        this.description = description;
        this.nativeFields = nativeFields;
    }

    /**
     * Combines both keys to create a usable category ID.
     *
     * @param hierarchy Primary key
     * @param category  Secondary key
     * @return Category ID
     */
    public static String combineIdKeys(String hierarchy, String category) {
        return hierarchy + "." + category;
    }

    /**
     * Returns the category's hierarchy identifier.
     *
     * @return Category's hierarchy ID
     */
    public String getHierarchyID() {
        return combineIdKeys(hierarchyName, hierarchyName);
    }

    /**
     * Returns the category's parent identifier.
     *
     * @return Category's parent ID
     */
    public String getParentID() {
        return combineIdKeys(hierarchyName, parentName);
    }

    public String getHierarchyName() {
        return hierarchyName;
    }

    public String getName() {
        return name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryField> getNativeFields() {
        return nativeFields;
    }

    /**
     * Returns the {@link CategoryField} whose name matches the specified one, if exists.
     *
     * @param fieldName Name of the category field
     * @return an {@code Optional} containing the field, or an empty {@code Optional} if not found
     */
    public Optional<CategoryField> getField(String fieldName) {
        return nativeFields.stream().
                filter(f -> f.getName().equals(fieldName))
                .findFirst();
    }

    public boolean addNativeField(CategoryField field) {
        return nativeFields.add(field);
    }

    public boolean removeNativeField(CategoryField field) {
        return nativeFields.remove(field);
    }

    public Category clone() {
        return new Category(
                id,
                hierarchyName,
                name,
                parentName,
                description,
                nativeFields.stream().map(CategoryField::clone).toList()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(hierarchyName, category.hierarchyName) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hierarchyName, name);
    }
}
