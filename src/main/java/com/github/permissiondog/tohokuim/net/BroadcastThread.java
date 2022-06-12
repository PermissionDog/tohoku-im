package com.github.permissiondog.tohokuim.net;

import com.github.permissiondog.tohokuim.Config;
import com.github.permissiondog.tohokuim.Constant;
import com.github.permissiondog.tohokuim.service.impl.ProfileServiceImpl;
import com.github.permissiondog.tohokuim.util.GsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class BroadcastThread implements Runnable{
    private static final Logger logger = LogManager.getLogger(BroadcastThread.class);
    @Override
    public void run() {
        var port = Config.getInstance().getDiscoverPort();
        logger.info("开始向 {} 端口广播", port);
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            logger.fatal("创建 DatagramSocket 失败", e);
            return;
        }
        while (!socket.isClosed()) {
            try {
                var address = new InetSocketAddress("255.255.255.255", port);

                var msg = new BroadcastMessage();
                msg.setUUID(Config.getInstance().getUUID());
                msg.setName(ProfileServiceImpl.getInstance().get().getName());
                msg.setSignature(ProfileServiceImpl.getInstance().get().getSignature());
                msg.setIp(InetAddress.getLocalHost());

                var data = GsonUtil.gson.toJson(msg).getBytes(StandardCharsets.UTF_8);

                var packet = new DatagramPacket(data, data.length, address);

                socket.send(packet);

                Thread.sleep(Constant.BROADCAST_INTERVAL);
            } catch (InterruptedException ignore) {
            } catch (UnknownHostException e) {
                logger.fatal("获取本机 ip 地址出错", e);
            } catch (IOException e) {
                logger.fatal("发送失败", e);
            }
        }
    }
}
