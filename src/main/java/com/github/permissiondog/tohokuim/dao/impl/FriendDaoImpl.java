package com.github.permissiondog.tohokuim.dao.impl;

import com.github.permissiondog.tohokuim.Config;
import com.github.permissiondog.tohokuim.Constant;
import com.github.permissiondog.tohokuim.dao.FriendDao;
import com.github.permissiondog.tohokuim.entity.Friend;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

public class FriendDaoImpl extends MultiDataDaoImpl<Friend> implements FriendDao {
    private static FriendDao instance;
    private FriendDaoImpl() {
    
    }
    public static FriendDao getInstance() {
        if (instance == null) {
            instance = new FriendDaoImpl();
        }
        return instance;
    }
    
    @Override
    public Type getType() {
        return new TypeToken<Collection<Friend>>() {}.getType();
    }

    @Override
    public String getFileName() {
        return Constant.FRIENDS_FILE;
    }

}
