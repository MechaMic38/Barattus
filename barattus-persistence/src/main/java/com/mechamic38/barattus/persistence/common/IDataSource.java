package com.mechamic38.barattus.persistence.common;

import java.util.List;

/**
 * Data source base interface.
 *
 * @param <T> Entity
 */
public interface IDataSource<T> {

    boolean insert(T entity);

    boolean update(T entity);

    List<T> getAll();
}
