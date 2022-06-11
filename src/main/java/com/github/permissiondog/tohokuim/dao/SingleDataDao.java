package com.github.permissiondog.tohokuim.dao;

public interface SingleDataDao<T> extends BaseDao<T> {
    T get();
    T update(T value);
}
