package com.github.permissiondog.tohokuim.net;

import com.github.permissiondog.tohokuim.entity.Message;
import com.github.permissiondog.tohokuim.entity.enumeration.MessageDirection;
import com.github.permissiondog.tohokuim.net.event.ReceiveMessageEvent;
import com.github.permissiondog.tohokuim.service.impl.MessageServiceImpl;
import com.github.permissiondog.tohokuim.util.GsonUtil;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            var r = GsonUtil.gson.newJsonReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            var type = r.nextString();
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
        var writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
        GsonUtil.gson.toJson(msg, writer);
    }
    private void handle(ReceiveMessageEvent event) throws IOException {
        var msg = new Message();
        msg.setUUID(event.getUUID());
        msg.setMessage(event.getMessage());
        msg.setSendTime(event.getSendTime());
        msg.setDirection(MessageDirection.Received);
        MessageServiceImpl.getInstance().add(msg);

        reply("ok");
    }
}
