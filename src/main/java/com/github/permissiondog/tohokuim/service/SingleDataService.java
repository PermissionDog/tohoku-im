package com.github.permissiondog.tohokuim.service;

public interface SingleDataService<T> extends BaseService<T> {
    T get();
    T update(T value);
}
