package com.github.permissiondog.tohokuim.dao.impl;

import com.github.permissiondog.tohokuim.dao.MultiDataDao;
import com.github.permissiondog.tohokuim.dao.Observer;
import com.github.permissiondog.tohokuim.entity.Identifiable;
import com.github.permissiondog.tohokuim.util.FileUtil;
import com.github.permissiondog.tohokuim.util.GsonUtil;

import java.util.*;
import java.util.function.Consumer;

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
    public Optional<T> get(UUID id) {
        synchronized (this) {
            return Optional.ofNullable(data.get(id));
        }
    }

    @Override
    public boolean update(T value) {
        synchronized (this) {
            if (!data.containsKey(value.getUUID())) {
                return false;
            }
            data.put(value.getUUID(), value);
            save();
            notifyListeners();
            return true;
        }
    }

    @Override
    public boolean remove(UUID id) {
        synchronized (this) {
            if (data.remove(id) == null) {
                return false;
            }
            save();
            notifyListeners();
            return true;
        }
    }

    @Override
    public T add(T value) {
        synchronized (this) {
            if (value.getUUID() == null) {
                value.setUUID(UUID.randomUUID());
            }
            data.put(value.getUUID(), value);
            save();
            notifyOnAddListeners(value);
            notifyListeners();
            return value;
        }
    }

    @Override
    public List<T> getAll() {
        synchronized (this) {
            return List.copyOf(data.values());
        }
    }

    private final List<Consumer<T>> onAddListeners = new LinkedList<>();
    @Override
    public void registerOnAddListener(Consumer<T> listener) {
        onAddListeners.add(listener);
    }
    private void notifyOnAddListeners(T newValue) {
        onAddListeners.forEach(c -> c.accept(newValue));
    }
}
