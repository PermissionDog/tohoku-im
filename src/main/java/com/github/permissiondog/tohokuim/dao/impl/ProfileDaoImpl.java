package com.github.permissiondog.tohokuim.dao.impl;

import com.github.permissiondog.tohokuim.Constant;
import com.github.permissiondog.tohokuim.dao.ProfileDao;
import com.github.permissiondog.tohokuim.entity.Profile;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ProfileDaoImpl extends SingleDataDaoImpl<Profile> implements ProfileDao {

    private static ProfileDao instance;
    private ProfileDaoImpl() {

    }
    public static ProfileDao getInstance() {
        if (instance == null) {
            instance = new ProfileDaoImpl();
        }
        return instance;
    }



    @Override
    public Type getType() {
        return new TypeToken<Profile>() {}.getType();
    }

    @Override
    public String getFileName() {
        return Constant.PROFILE_FILE;
    }
}
