package com.github.permissiondog.tohokuim.dao;

import com.github.permissiondog.tohokuim.entity.Identifiable;

import java.util.List;
import java.util.UUID;

public interface MultiDataDao<T extends Identifiable> extends BaseDao<T>{
    T get(UUID id);
    T update(T value);
    T remove(T value);
    T add(T value);
    List<T> getAll();
}
