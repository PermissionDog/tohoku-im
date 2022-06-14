package com.github.permissiondog.tohokuim.net;

import com.github.permissiondog.tohokuim.entity.Message;
import com.github.permissiondog.tohokuim.entity.enumeration.MessageDirection;
import com.github.permissiondog.tohokuim.net.event.ReceiveMessageEvent;
import com.github.permissiondog.tohokuim.service.impl.FriendServiceImpl;
import com.github.permissiondog.tohokuim.service.impl.MessageServiceImpl;
import com.github.permissiondog.tohokuim.util.GsonUtil;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ReceiveThread implements Runnable{
    private final Socket socket;

    public ReceiveThread(Socket socket) {
        this.socket = socket;
    }

    private static final Logger logger = LogManager.getLogger(ReceiveThread.class);
    @Override
    public void run() {

        try {
            logger.trace("获取 InputStream");
            var in = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
            logger.trace("创建 Reader");
            var r = GsonUtil.gson.newJsonReader(in);
            logger.trace("读取消息类型");
            String type = GsonUtil.gson.fromJson(r, String.class);
            logger.trace("接收消息 {}", type);
            switch (type) {
                case ReceiveMessageEvent.NAME -> handle(GsonUtil.gson.fromJson(r, ReceiveMessageEvent.class));
            }
            socket.close();
        } catch (IOException e) {
            logger.debug("连接断开: {}", socket.getInetAddress());
        }
    }
    private void reply(String msg) throws IOException {
        logger.trace("回复 {}", msg);
        var writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
        logger.trace("创建 writer");
        GsonUtil.gson.toJson(msg, writer);
        writer.flush();
        logger.trace("写完成");
    }
    private void handle(ReceiveMessageEvent event) throws IOException {
        logger.trace("处理 ReceiveMessageEvent");
        var msg = new Message();
        msg.setUUID(event.getUUID());
        msg.setMessage(event.getMessage());
        msg.setSendTime(event.getSendTime());
        msg.setDirection(MessageDirection.Received);
        msg.setSession(event.getSender());

        logger.trace("添加消息");
        MessageServiceImpl.getInstance().add(msg);
        logger.trace("更新 IP 地址");
        FriendServiceImpl.getInstance().updateFriendAddress(event.getSender(), socket.getInetAddress());


        logger.trace("回复消息");
        reply("ok");
    }
}
