package com.github.permissiondog.tohokuim.dao.impl;

import com.github.permissiondog.tohokuim.Constant;
import com.github.permissiondog.tohokuim.dao.MessageDao;
import com.github.permissiondog.tohokuim.entity.Message;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

public class MessageDaoImpl extends MultiDataDaoImpl<Message> implements MessageDao {
    private static MessageDao instance;
    private MessageDaoImpl() {
    
    }
    public static MessageDao getInstance() {
        if (instance == null) {
            instance = new MessageDaoImpl();
        }
        return instance;
    }

    @Override
    public Type getType() {
        return new TypeToken<Collection<Message>>() {}.getType();
    }

    @Override
    public String getFileName() {
        return Constant.MESSAGES_FILE;
    }
}
