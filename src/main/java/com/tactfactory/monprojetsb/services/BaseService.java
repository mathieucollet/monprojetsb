package com.tactfactory.monprojetsb.services;

import java.util.List;

public interface BaseService<T, Long> {
    T save(T item);

    void delete(T item);

    T findById(Long id);

    List<T> findAll();
}

