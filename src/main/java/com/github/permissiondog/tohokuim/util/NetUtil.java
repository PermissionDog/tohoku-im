package com.github.permissiondog.tohokuim.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetUtil {
    private static final Logger logger = LogManager.getLogger(NetUtil.class);
    public static List<NetworkInterface> getNetworkInterfaces() {
        try {
            return Collections.list(NetworkInterface.getNetworkInterfaces());
        } catch (SocketException e) {
            logger.fatal("获取网卡出错", e);
            System.exit(1);
        }
        return null;
    }

    public static List<InetAddress> getInetAddresses() {
        return getNetworkInterfaces().stream().flatMap(networkInterface ->
            Collections.list(networkInterface.getInetAddresses()).stream()
        ).toList();
    }
}
