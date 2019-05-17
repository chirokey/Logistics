package com.logistics.dao.generic;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {
    PK create(T object);

    T read(PK id);

    void update(T object);

    void delete(T object);

    void delete(PK id);

    List<T> readAll();

    Long countRows();

    List<T> readLimit(int offset, int count);
}
