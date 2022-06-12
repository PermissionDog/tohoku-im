package com.github.permissiondog.tohokuim.service.impl;

import com.github.permissiondog.tohokuim.Config;
import com.github.permissiondog.tohokuim.dao.Observer;
import com.github.permissiondog.tohokuim.dao.impl.MessageDaoImpl;
import com.github.permissiondog.tohokuim.entity.Message;
import com.github.permissiondog.tohokuim.entity.enumeration.MessageDirection;
import com.github.permissiondog.tohokuim.service.MessageService;
import com.github.permissiondog.tohokuim.service.exception.NetworkException;
import com.github.permissiondog.tohokuim.service.exception.NoSuchFriendException;
import com.github.permissiondog.tohokuim.net.ReceiveThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    private static final Logger logger = LogManager.getLogger(MessageServiceImpl.class);
    
    @Override
    public void registerListener(Observer observer) {
        MessageDaoImpl.getInstance().registerListener(observer);
    }

    @Override
    public Optional<Message> get(UUID id) {
        return MessageDaoImpl.getInstance().get(id);
    }

    @Override
    public boolean update(Message value) {
        return MessageDaoImpl.getInstance().update(value);
    }

    @Override
    public boolean remove(UUID id) {
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

    @Override
    public Message sendMessage(UUID target, String body) throws NoSuchFriendException, NetworkException {
        // 如果朋友不存在就抛出异常
        FriendServiceImpl.getInstance().get(target).orElseThrow(NoSuchFriendException::new);

        var msg = new Message();
        msg.setSendTime(LocalDateTime.now());
        msg.setDirection(MessageDirection.Sent);
        msg.setMessage(body);

        sendMessage(msg);


        return msg;
    }

    private void sendMessage(Message msg) throws NetworkException {

    }

    public void initNet() throws NetworkException {
        initMessageReceiver();
        initFriendDiscover();
        initBroadCast();
    }
    private void initMessageReceiver() {
        var port = Config.getInstance().getMsgPort();
        logger.info("监听消息接收端口 {}", port);
        try {
            var serverSocket = new ServerSocket(port);
            new Thread(() -> {
                Socket socket;
                while (!serverSocket.isClosed()) {
                    try {
                        socket = serverSocket.accept();
                        new Thread(new ReceiveThread(socket)).start();
                    } catch (IOException e) {
                        // 这里是否需要 break
                        logger.error("accept", e);
                    }

                }
            }).start();
        } catch (IOException e) {
            logger.fatal("监听端口失败", e);
            throw new NetworkException("");
        }
    }
    private void initFriendDiscover() {
        
    }
    private void initBroadCast() {

    }
}
