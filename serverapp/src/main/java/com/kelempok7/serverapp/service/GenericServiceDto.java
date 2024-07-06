package com.kelempok7.serverapp.service;
import java.util.List;


public interface GenericServiceDto <T,ID,DTO> {
    List<T> getAll();
    T getById(ID id);

    T create(DTO entity);

    T update(ID id,DTO entity);

    T delete(ID id);
}
