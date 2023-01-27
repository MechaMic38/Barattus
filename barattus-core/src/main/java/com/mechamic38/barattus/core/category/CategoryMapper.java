package com.mechamic38.barattus.core.category;

import com.mechamic38.barattus.persistence.category.CategoryDTO;
import com.mechamic38.barattus.persistence.category.CategoryFieldDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for categories and fields.
 */
class CategoryMapper {

    /**
     * Converts the given Category entity into a CategoryDTO object.
     *
     * @param category Category entity to map
     * @return CategoryDTO object
     */
    public CategoryDTO toDto(Category category) {
        List<CategoryFieldDTO> fields = category.getNativeFields().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return new CategoryDTO(
                category.getID(),
                category.getHierarchyName(),
                category.getParentName(),
                category.getName(),
                category.getDescription(),
                fields
        );
    }

    /**
     * Converts the given CategoryField entity into a CategoryFieldDTO object.
     *
     * @param field CategoryField entity to map
     * @return CategoryFieldDTO object
     */
    public CategoryFieldDTO toDto(CategoryField field) {
        return new CategoryFieldDTO(
                field.getName(),
                field.getFieldType().name(),
                field.isMandatory()
        );
    }

    /**
     * Converts the given CategoryDTO object into a Category entity.
     *
     * @param categoryDTO CategoryDTO object to map
     * @return Category entity
     */
    public Category fromDto(CategoryDTO categoryDTO) {
        List<CategoryField> fields = categoryDTO.getNativeFields().stream()
                .map(this::fromDto)
                .toList();

        Category category = new Category(
                categoryDTO.getHierarchyName(),
                categoryDTO.getCategoryName(),
                categoryDTO.getParentName(),
                categoryDTO.getDescription()
        );
        fields.forEach(category::addNativeField);

        return category;
    }

    /**
     * Converts the given FieldDTO object into a Field entity.
     *
     * @param fieldDTO FieldDTO object to map
     * @return Field entity
     */
    public CategoryField fromDto(CategoryFieldDTO fieldDTO) {
        return new CategoryField(
                fieldDTO.getName(),
                CategoryField.Type.valueOf(fieldDTO.getFieldType()),
                fieldDTO.isMandatory()
        );
    }
}
