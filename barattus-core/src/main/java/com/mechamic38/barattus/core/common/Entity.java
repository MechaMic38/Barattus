package com.mechamic38.barattus.core.common;

import java.util.Objects;

/**
 * Base class for entities.
 *
 * @param <K> Type of primary key used as identifier
 */
public abstract class Entity<K> {

    protected K id;


    public K getID() {
        return id;
    }

    protected void setID(K id) {
        this.id = id;
    }

    public boolean checkID(K id) {
        return this.id.equals(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Entity<?> entity = (Entity<?>) obj;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
