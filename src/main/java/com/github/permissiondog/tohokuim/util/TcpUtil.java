package com.github.permissiondog.tohokuim.util;

import com.github.permissiondog.tohokuim.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpUtil {

    private static final Logger logger = LogManager.getLogger(TcpUtil.class);

    /**
     * 建立 TCP 短连接, 发送消息后接收 "OK"
     * @param address   发送地址
     * @param data      数据
     * @return  如果接收到 "OK" 返回 true
     */
    public static boolean sendMessage(InetAddress address, String data) {
        var socketAddress = new InetSocketAddress(address, Config.getInstance().getMsgPort());
        logger.trace("发送消息 {} {}", socketAddress, data);
        try (var socket = new Socket()) {
            logger.trace("建立连接");
            socket.connect(socketAddress);
            logger.trace("连接成功");
            var writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            logger.trace("创建输出流");
            writer.write(data);
            writer.flush();
            logger.trace("写出数据");
            var reader = new InputStreamReader(socket.getInputStream());
            logger.trace("读取返回值");
            var jsonReader = GsonUtil.gson.newJsonReader(reader);
            if ("ok".equals(jsonReader.nextString())) {
                logger.trace("发送成功");
                return true;
            }
            logger.trace("发送失败");
        } catch (IOException e) {
            logger.trace(e);
            return false;
        }
        return false;
    }
}
