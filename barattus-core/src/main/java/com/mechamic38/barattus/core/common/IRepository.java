package com.mechamic38.barattus.core.common;

import java.util.List;

/**
 * Base repository interface for entities.
 *
 * @param <T> Entity
 * @param <K> Entity Identifier Type
 */
public interface IRepository<T extends Entity<K>, K> {

    T getById(K id);

    void save(T entity);

    //List<T> query();
}
