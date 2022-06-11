package com.github.permissiondog.tohokuim.service;

import com.github.permissiondog.tohokuim.entity.Identifiable;

import java.util.List;
import java.util.UUID;

public interface MultiDataService<T extends Identifiable> extends BaseService<T> {
    T get(UUID id);
    T update(T value);
    T remove(UUID id);
    T add(T value);
    List<T> getAll();
}
