package com.github.permissiondog.tohokuim.service;

import com.github.permissiondog.tohokuim.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService extends MultiDataService<Message> {
    /**
     * 使用会话寻找消息列表
     * @param session   会话 ID
     * @return          找到的信息
     */
    List<Message> getAll(UUID session);
}
