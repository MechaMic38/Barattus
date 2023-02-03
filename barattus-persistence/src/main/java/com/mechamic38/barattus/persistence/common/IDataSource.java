package com.mechamic38.barattus.persistence.common;

/**
 * Data source base interface.
 *
 * @param <T> Entity
 */
public interface IDataSource<T> {

    boolean insert(T entity);

    boolean update(T entity);
}
