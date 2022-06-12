package com.github.permissiondog.tohokuim.dao;

import com.github.permissiondog.tohokuim.entity.Identifiable;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public interface MultiDataDao<T extends Identifiable> extends BaseDao<T>{
    T get(UUID id);
    T update(T value);
    T remove(UUID id);
    T add(T value);
    List<T> getAll();
    void registerOnAddListener(Consumer<T> listener);
}
