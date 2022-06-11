package com.github.permissiondog.tohokuim.service.impl;

import com.github.permissiondog.tohokuim.dao.Observer;
import com.github.permissiondog.tohokuim.dao.impl.ProfileDaoImpl;
import com.github.permissiondog.tohokuim.entity.Profile;
import com.github.permissiondog.tohokuim.service.ProfileService;

public class ProfileServiceImpl implements ProfileService {
    
    private static ProfileService instance;
    private ProfileServiceImpl() {
    
    }
    public static ProfileService getInstance() {
        if (instance == null) {
            instance = new ProfileServiceImpl();
        }
        return instance;
    }

    @Override
    public void registerListener(Observer observer) {
        ProfileDaoImpl.getInstance().registerListener(observer);
    }

    @Override
    public Profile get() {
        return ProfileDaoImpl.getInstance().get();
    }

    @Override
    public Profile update(Profile value) {
        return ProfileDaoImpl.getInstance().update(value);
    }
}
