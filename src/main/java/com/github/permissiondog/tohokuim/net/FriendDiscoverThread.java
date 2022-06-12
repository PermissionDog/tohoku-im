package com.github.permissiondog.tohokuim.net;

import com.github.permissiondog.tohokuim.Config;
import com.github.permissiondog.tohokuim.entity.Friend;
import com.github.permissiondog.tohokuim.service.exception.NetworkException;
import com.github.permissiondog.tohokuim.service.impl.FriendServiceImpl;
import com.github.permissiondog.tohokuim.util.GsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class FriendDiscoverThread implements Runnable {
    private static final Logger logger = LogManager.getLogger(FriendDiscoverThread.class);
    @Override
    public void run() {
        var port = Config.getInstance().getDiscoverPort();
        logger.info("监听好友发现端口 {}", port);
        var buf = new byte[10000];
        try {
            var socket = new DatagramSocket(port);
            while (!socket.isClosed()) {
                var packet = new DatagramPacket(buf, buf.length);
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    break;
                }
                var reader = new InputStreamReader(new ByteArrayInputStream(buf), StandardCharsets.UTF_8);
                var jsonReader = GsonUtil.gson.newJsonReader(reader);
                BroadcastMessage broadcastMessage = GsonUtil.gson.fromJson(jsonReader, BroadcastMessage.class);

                final var uuid = broadcastMessage.getUUID();
                logger.trace("接收到 UUID {}", uuid);
                if (uuid.equals(Config.getInstance().getUUID())) {
                    logger.trace("接收到自己的 UUID");
                    continue;
                }
                if (FriendServiceImpl.getInstance().get(uuid).isPresent()) {
                    logger.trace("接收到重复的 UUID");
                    continue;
                }
                logger.info("发现新朋友 {} ({})", broadcastMessage.getName(), uuid);
                var friend = new Friend();
                friend.setUUID(broadcastMessage.getUUID());
                friend.setName(broadcastMessage.getName());
                friend.setSignature(broadcastMessage.getSignature());
                FriendServiceImpl.getInstance().add(friend);
            }
            socket.close();
        } catch (SocketException e) {
            logger.fatal("监听端口失败", e);
            throw new NetworkException("无法监听好友发现端口");
        }
    }
}
