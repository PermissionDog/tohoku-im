package com.github.permissiondog.tohokuim.dao.impl;

import com.github.permissiondog.tohokuim.dao.Observer;
import com.github.permissiondog.tohokuim.dao.SingleDataDao;
import com.github.permissiondog.tohokuim.util.FileUtil;
import com.github.permissiondog.tohokuim.util.GsonUtil;

import java.util.LinkedList;
import java.util.List;

public abstract class SingleDataDaoImpl<T> extends BaseDaoImpl<T> implements SingleDataDao<T> {

    private T value;
    private final List<Observer> observerList = new LinkedList<>();

    @Override
    public void load() {
        var json = FileUtil.readFile(getFileName());
        value = GsonUtil.gson.fromJson(json, getType());
    }

    @Override
    public void save() {
        var json = GsonUtil.gson.toJson(value, getType());
        FileUtil.writeFile(getFileName(), json);
    }

    @Override
    public void registerListener(Observer observer) {
        observerList.add(observer);
    }

    private void notifyListeners() {
        observerList.forEach(Observer::onChange);
    }

    @Override
    public T get() {
        synchronized (this) {
            return value;
        }
    }

    @Override
    public T update(T value) {
        synchronized (this) {
            this.value = value;
            save();
            notifyListeners();
            return value;
        }
    }
}
