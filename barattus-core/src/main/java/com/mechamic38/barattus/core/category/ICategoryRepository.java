package com.mechamic38.barattus.core.category;

import com.mechamic38.barattus.core.common.IRepository;

import java.util.List;

/**
 * Category repository base interface
 */
public interface ICategoryRepository extends IRepository<Category, String> {

    void importData(List<Category> categories);
}
