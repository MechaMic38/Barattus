package com.mechamic38.barattus.gui.offer;

import com.mechamic38.barattus.core.category.Category;
import com.mechamic38.barattus.core.category.CategoryField;
import com.mechamic38.barattus.core.category.ICategoryService;
import com.mechamic38.barattus.core.offer.IOfferService;
import com.mechamic38.barattus.core.offer.Offer;
import com.mechamic38.barattus.core.offer.OfferField;
import com.mechamic38.barattus.gui.common.SessionState;
import com.mechamic38.barattus.util.Result;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CreateOfferViewModel implements ICreateOfferViewModel {

    private final ICategoryService categoryService;
    private final IOfferService offerService;

    private final StringProperty error = new SimpleStringProperty("");
    private final ListProperty<Category> rootCategories = new SimpleListProperty<>();
    private final ListProperty<Category> leafCategories = new SimpleListProperty<>();
    private final ListProperty<CategoryField> fields = new SimpleListProperty<>();

    public CreateOfferViewModel(ICategoryService categoryService, IOfferService offerService) {
        this.categoryService = categoryService;
        this.offerService = offerService;
    }

    @Override
    public void loadSubcategories(Category category) {
        leafCategories.set(FXCollections.observableList(
                categoryService.getLeafCategories(category)
        ));
    }

    @Override
    public void loadCategoryFields(Category category) {
        if (category == null) return;
        fields.set(FXCollections.observableList(
                categoryService.getCategoryFields(category)
        ));
    }

    @Override
    public boolean publishOffer(Category category, String offerTitle, HashMap<CategoryField, String> fields) {
        if (!checkMandatoryFields(fields)) {
            error.set("offer.create.error.field.all");
            return false;
        }

        Result<Offer> result = offerService.createOffer(
                SessionState.getInstance().getUser(),
                category,
                offerTitle,
                prepareOfferFields(fields)
        );

        if (result.isError()) {
            error.set(result.getError());
            return false;
        } else {
            SessionState.getInstance().setOffer(result.getResult());
            return true;
        }
    }

    private boolean checkMandatoryFields(HashMap<CategoryField, String> fields) {
        for (CategoryField fieldDTO : fields.keySet()) {
            if (fieldDTO.getMandatory()) {
                if (fields.get(fieldDTO).isEmpty()) return false;
            }
        }

        return true;
    }

    private List<OfferField> prepareOfferFields(HashMap<CategoryField, String> fields) {
        List<OfferField> offerFields = new LinkedList<>();
        for (CategoryField field : fields.keySet()) {
            offerFields.add(new OfferField(
                    field.getName(),
                    OfferField.Type.valueOf(field.getFieldType().name()),
                    fields.get(field)
            ));
        }
        return offerFields;
    }

    @Override
    public void initialize() {
        rootCategories.set(FXCollections.observableList(
                categoryService.getHierarchies()
        ));
    }

    @Override
    public StringProperty errorProperty() {
        return error;
    }

    @Override
    public ListProperty<Category> rootCategoriesProperty() {
        return rootCategories;
    }

    @Override
    public ListProperty<Category> leafCategoriesProperty() {
        return leafCategories;
    }

    @Override
    public ListProperty<CategoryField> fieldsProperty() {
        return fields;
    }
}
