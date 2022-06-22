package com.github.permissiondog.tohokuim.net;

import com.github.permissiondog.tohokuim.Config;
import com.github.permissiondog.tohokuim.Constant;
import com.github.permissiondog.tohokuim.service.impl.ProfileServiceImpl;
import com.github.permissiondog.tohokuim.util.GsonUtil;
import com.github.permissiondog.tohokuim.util.NetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class BroadcastThread implements Runnable{
    private static final Logger logger = LogManager.getLogger(BroadcastThread.class);

    private void broadcast(InetAddress localAddress) {
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(new InetSocketAddress(localAddress, 0));
        } catch (SocketException e) {
            logger.warn("创建 socket 失败, ip: {}", localAddress);
            return;
        }

        var address = new InetSocketAddress("255.255.255.255", Config.getInstance().getDiscoverPort());

        var msg = new BroadcastMessage();
        msg.setUUID(Config.getInstance().getUUID());
        msg.setName(ProfileServiceImpl.getInstance().get().getName());
        msg.setSignature(ProfileServiceImpl.getInstance().get().getSignature());
        msg.setIp(localAddress);

        var data = GsonUtil.gson.toJson(msg).getBytes(StandardCharsets.UTF_8);
        var packet = new DatagramPacket(data, data.length, address);

        try {
            socket.send(packet);
        } catch (IOException e) {
            logger.warn("发送失败, local: {}, target: {}", localAddress, address);
        }
        socket.close();
    }
    @Override
    public void run() {
        var port = Config.getInstance().getDiscoverPort();
        logger.info("开始向 {} 端口广播", port);

        while (true) {
            NetUtil.getInetAddresses().stream().parallel()
                    .filter(address -> !address.isLoopbackAddress())    // 过滤掉本机地址
                    .filter(address -> address instanceof Inet4Address) // 过滤掉非 IPV4 地址
                    .forEach(this::broadcast);                          // 发送广播
            try {
                Thread.sleep(Constant.BROADCAST_INTERVAL);
            } catch (InterruptedException ignore) {
            }
        }
    }
}
