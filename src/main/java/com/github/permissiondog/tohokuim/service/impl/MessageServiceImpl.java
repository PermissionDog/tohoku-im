package com.github.permissiondog.tohokuim.service.impl;

import com.github.permissiondog.tohokuim.dao.Observer;
import com.github.permissiondog.tohokuim.dao.impl.MessageDaoImpl;
import com.github.permissiondog.tohokuim.entity.Message;
import com.github.permissiondog.tohokuim.service.MessageService;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class MessageServiceImpl implements MessageService {
    private static MessageService instance;
    private MessageServiceImpl() {
    
    }
    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageServiceImpl();
        }
        return instance;
    }

    @Override
    public void registerListener(Observer observer) {
        MessageDaoImpl.getInstance().registerListener(observer);
    }

    @Override
    public Message get(UUID id) {
        return MessageDaoImpl.getInstance().get(id);
    }

    @Override
    public Message update(Message value) {
        return MessageDaoImpl.getInstance().update(value);
    }

    @Override
    public Message remove(UUID id) {
        return MessageDaoImpl.getInstance().remove(id);
    }

    @Override
    public Message add(Message value) {
        return MessageDaoImpl.getInstance().add(value);
    }

    @Override
    public List<Message> getAll() {
        return MessageDaoImpl.getInstance().getAll();
    }

    @Override
    public void registerOnAddListener(Consumer<Message> listener) {
        MessageDaoImpl.getInstance().registerOnAddListener(listener);
    }

    @Override
    public List<Message> getAll(UUID session) {
        return getAll().stream().filter(message -> message.getSession().equals(session)).toList();
    }
}
