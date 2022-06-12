package com.github.permissiondog.tohokuim.service;

import com.github.permissiondog.tohokuim.entity.Message;
import com.github.permissiondog.tohokuim.service.exception.NetworkException;
import com.github.permissiondog.tohokuim.service.exception.NoSuchFriendException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService extends MultiDataService<Message> {
    /**
     * 使用会话寻找消息列表
     * @param session   会话 ID
     * @return          找到的信息
     */
    List<Message> getAll(UUID session);

    /**
     * 发送消息
     * @param target                    目标朋友 ID
     * @param body                      消息体
     * @return                          成功返回消息
     * @throws NoSuchFriendException    未找到朋友时抛出
     * @throws NetworkException              网络原因导致无法发出
     */
    Message sendMessage(UUID target, String body) throws NoSuchFriendException, NetworkException;

    // 网络相关

    /**
     * 网络初始化
     * 包括:
     *  监听消息接收 TCP 端口
     *  监听好友发现 UDP 端口
     *  开启定时发送好友发现消息线程
     * @throws NetworkException 初始化失败时抛出
     */
    void initNet() throws NetworkException;
}
