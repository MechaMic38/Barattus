package com.mechamic38.barattus.core.category;

import com.mechamic38.barattus.persistence.category.CategoryDTO;
import com.mechamic38.barattus.persistence.category.ICategoryDataSource;
import com.mechamic38.barattus.persistence.common.InvalidFileException;
import com.mechamic38.barattus.util.Result;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategoryService implements ICategoryService {

    private final ICategoryRepository repository;
    private final ICategoryDataSource dataSource;
    private final CategoryMapper mapper;

    public CategoryService(ICategoryRepository repository, ICategoryDataSource dataSource, CategoryMapper mapper) {
        this.repository = repository;
        this.dataSource = dataSource;
        this.mapper = mapper;
    }

    @Override
    public Result<Category> addHierarchy(String name, String description) {
        if (repository.getById(Category.combineIdKeys(name, name)) != null) {
            return Result.error("category.error.hierarchy.exists");
        }

        Category hierarchy = new Category(
                name,
                name,
                null,
                description
        );
        getHierarchyDefaultFields().forEach(hierarchy::addNativeField);

        repository.save(hierarchy);
        return Result.success(hierarchy);
    }

    @Override
    public Result<Category> addCategory(Category category, String name, String description) {
        if (repository.getById(Category.combineIdKeys(category.getHierarchyName(), name)) != null) {
            return Result.error("category.error.category.exists");
        }

        Category newCategory = new Category(
                category.getHierarchyName(),
                name,
                category.getName(),
                description
        );

        repository.save(newCategory);
        return Result.success(newCategory);
    }

    @Override
    public Result<Category> updateCategory(Category category, String description) {
        category.setDescription(description);
        repository.save(category);
        return Result.success(category);
    }

    @Override
    public Result<CategoryField> addField(Category category, String name, CategoryField.Type type, boolean mandatory) {
        if (findFieldByCategoryAndName(category, name) != null) {
            return Result.error("category.error.field.exists");
        }

        CategoryField field = new CategoryField(name, type, mandatory);
        category.addNativeField(field);

        repository.save(category);
        return Result.success(field);
    }

    @Override
    public Result<CategoryField> updateField(Category category, CategoryField field, String name, CategoryField.Type type, boolean mandatory) {
        CategoryField categoryField;
        if (field.getName().equals(name)) {
            categoryField = findNativeFieldByCategoryAndName(category, name);
            categoryField.setFieldType(type);
            categoryField.setMandatory(mandatory);
            return Result.success(categoryField);
        }


        if ((categoryField = findFieldByCategoryAndName(category, name)) != null) {
            return Result.error("category.error.field.exists");
        }

        category.getNativeFields().remove(field);
        categoryField = new CategoryField(name, type, mandatory);
        category.getNativeFields().add(categoryField);

        repository.save(category);
        return Result.success(categoryField);
    }

    @Override
    public Result<CategoryField> deleteField(Category category, CategoryField field) {
        category.removeNativeField(field);

        repository.save(category);
        return Result.success(field);
    }

    @Override
    public Result<List<Category>> loadParamsFromFile(String path) {
        try {
            List<CategoryDTO> dtoList = dataSource.getAll(path);
            List<Category> categories = dtoList.stream().map(mapper::fromDto).toList();
            repository.importData(categories);
            return Result.success(categories);
        } catch (InvalidFileException e) {
            return Result.error("category.load.error");
        }
    }

    @Override
    public List<Category> getHierarchies() {
        return getRoots();
    }

    @Override
    public List<Category> getSubcategories(Category category) {
        return getDescendants(category);
    }

    @Override
    public List<Category> getDirectSubcategories(Category category) {
        return getChildren(category);
    }

    @Override
    public List<Category> getLeafCategories(Category category) {
        return getDescendants(category).stream()
                .filter(this::isLeaf)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryField> getCategoryFields(Category category) {
        List<CategoryField> leafFields = category.getNativeFields();

        List<CategoryField> parentFields = getAncestors(category).stream()
                .map(Category::getNativeFields)
                .flatMap(Collection::stream).toList();

        return Stream.of(leafFields, parentFields)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    //*****************************************************
    // OTHER
    //*****************************************************

    /**
     * {@inheritDoc}
     */
    public CategoryField findNativeFieldByCategoryAndName(Category category, String fieldName) {
        return category.getNativeFields().stream()
                .filter(f -> f.getName().equals(fieldName))
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    public CategoryField findFieldByCategoryAndName(Category category, String fieldName) {
        final List<Category> ancestors = this.getAncestors(category);
        return Stream.concat(
                        category.getNativeFields().stream(),
                        ancestors.stream()
                                .map(Category::getNativeFields)
                                .flatMap(List::stream)
                ).filter(f -> f.getName().equals(fieldName))
                .findFirst()
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    public List<Category> getChildren(Category parent) {
        return repository.getAll().stream()
                .filter(element -> parent.checkID(element.getParentID()))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    public List<Category> getAncestors(Category category) {
        return Optional.ofNullable(this.getParent(category))
                .map(this::getAncestorsByParent)
                .orElse(Collections.emptyList());
    }

    /**
     * Gets the ancestors the of the parent category.
     *
     * @param parent Parent category
     * @return List of ancestors
     */
    private List<Category> getAncestorsByParent(Category parent) {
        return Stream.concat(
                Stream.of(parent),
                this.getAncestors(parent).stream()
        ).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    public List<Category> getDescendants(Category category) {
        final List<Category> children = this.getChildren(category);
        return children.isEmpty() ? Collections.emptyList() : Stream.concat(
                children.stream(),
                children.stream()
                        .map(this::getDescendants)
                        .flatMap(Collection::stream)
        ).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    public Category getParent(Category category) {
        return Optional.ofNullable(category.getParentID())
                .map(repository::getById)
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    public List<Category> getRoots() {
        return repository.getAll().stream()
                .filter(this::isRoot)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    public boolean isAncestral(Category ancestral, Category element) {
        return this.isDescendant(element, ancestral);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isDescendant(Category element, Category ancestral) {
        return Optional.ofNullable(this.getParent(element))
                .map(parent -> this.checkIsDescendant(ancestral, parent))
                .orElse(false);
    }

    private boolean checkIsDescendant(Category ancestral, Category parent) {
        return parent.checkID(ancestral.getID()) || this.isDescendant(parent, ancestral);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isLeaf(Category category) {
        return this.getChildren(category).isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isRoot(Category category) {
        return Optional.ofNullable(category.getParentName()).isEmpty();
    }

    /**
     * Gets the default fields for any new hierarchy.
     *
     * @return List of default fields
     */
    private List<CategoryField> getHierarchyDefaultFields() {
        return Arrays.asList(
                new CategoryField("Stato di conservazione", CategoryField.Type.STRING, true),
                new CategoryField("Descrizione libera", CategoryField.Type.STRING, false)
        );
    }
}
