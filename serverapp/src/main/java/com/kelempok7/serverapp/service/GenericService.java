package com.kelempok7.serverapp.service;

import java.util.List;

public interface GenericService <T,ID> {
    List<T> getAll();
    T getById(ID id);

    T create(T entity);

    T update(ID id,T entity);

    T delete(ID id);
}
