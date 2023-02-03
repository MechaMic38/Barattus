package com.mechamic38.barattus.persistence.category;

import com.mechamic38.barattus.persistence.common.IDataSource;
import com.mechamic38.barattus.persistence.common.InvalidFileException;

import java.util.List;

/**
 * Category data source base interface.
 */
public interface ICategoryDataSource extends IDataSource<CategoryDTO> {

    List<CategoryDTO> getAll();

    List<CategoryDTO> getAll(String filePath) throws InvalidFileException;
}
