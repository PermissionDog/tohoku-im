package com.github.permissiondog.tohokuim.service.impl;

import com.github.permissiondog.tohokuim.dao.Observer;
import com.github.permissiondog.tohokuim.dao.impl.FriendDaoImpl;
import com.github.permissiondog.tohokuim.entity.Friend;
import com.github.permissiondog.tohokuim.service.FriendService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class FriendServiceImpl implements FriendService {

    private static FriendService instance;
    private FriendServiceImpl() {
    
    }
    public static FriendService getInstance() {
        if (instance == null) {
            instance = new FriendServiceImpl();
        }
        return instance;
    }
    
    @Override
    public void registerListener(Observer observer) {
        FriendDaoImpl.getInstance().registerListener(observer);
    }

    @Override
    public Optional<Friend> get(UUID id) {
        return FriendDaoImpl.getInstance().get(id);
    }

    @Override
    public boolean update(Friend value) {
        return FriendDaoImpl.getInstance().update(value);
    }

    @Override
    public boolean remove(UUID id) {
        return FriendDaoImpl.getInstance().remove(id);
    }

    @Override
    public Friend add(Friend value) {
        return FriendDaoImpl.getInstance().add(value);
    }

    @Override
    public List<Friend> getAll() {
        return FriendDaoImpl.getInstance().getAll();
    }

    @Override
    public void registerOnAddListener(Consumer<Friend> listener) {
        FriendDaoImpl.getInstance().registerOnAddListener(listener);
    }
}
