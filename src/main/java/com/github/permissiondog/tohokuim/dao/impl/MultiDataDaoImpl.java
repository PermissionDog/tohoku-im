package com.github.permissiondog.tohokuim.dao.impl;

import com.github.permissiondog.tohokuim.dao.MultiDataDao;
import com.github.permissiondog.tohokuim.dao.Observer;
import com.github.permissiondog.tohokuim.entity.Identifiable;
import com.github.permissiondog.tohokuim.util.FileUtil;
import com.github.permissiondog.tohokuim.util.GsonUtil;

import java.util.*;

public abstract class MultiDataDaoImpl<T extends Identifiable> extends BaseDaoImpl<T> implements MultiDataDao<T> {

    private Map<UUID, T> data;
    private final List<Observer> observerList = new LinkedList<>();

    @Override
    public void load() {
        data = new HashMap<>();
        var json = FileUtil.readFile(getFileName());
        Collection<T> dataList = GsonUtil.gson.fromJson(json, getType());
        dataList.forEach(t -> data.put(t.getUUID(), t));
    }

    @Override
    public void save() {
        var json = GsonUtil.gson.toJson(data.values(), getType());
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
    public T get(UUID id) {
        synchronized (this) {
            return data.get(id);
        }
    }

    @Override
    public T update(T value) {
        synchronized (this) {
            if (!data.containsKey(value.getUUID())) {
                return null;
            }
            data.put(value.getUUID(), value);
            save();
            notifyListeners();
            return value;
        }
    }

    @Override
    public T remove(UUID id) {
        synchronized (this) {
            return data.remove(id);
        }
    }

    @Override
    public T add(T value) {
        synchronized (this) {
            if (data.containsKey(value.getUUID())) {
                return null;
            }
            data.put(value.getUUID(), value);
            return value;
        }
    }

    @Override
    public List<T> getAll() {
        synchronized (this) {
            return List.copyOf(data.values());
        }
    }
}
