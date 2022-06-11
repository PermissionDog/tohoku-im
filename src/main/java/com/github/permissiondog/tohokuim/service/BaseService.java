package com.github.permissiondog.tohokuim.service;

import com.github.permissiondog.tohokuim.dao.Observer;

public interface BaseService<T> {
    void registerListener(Observer observer);
}
