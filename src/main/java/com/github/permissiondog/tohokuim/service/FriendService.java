package com.github.permissiondog.tohokuim.service;

import com.github.permissiondog.tohokuim.entity.Friend;
import com.github.permissiondog.tohokuim.entity.Message;

import java.net.InetAddress;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FriendService extends MultiDataService<Friend> {
    // 网络相关

    /**
     * 更新朋友的 IP 地址
     * @param friendID  朋友 ID
     * @param address   IP 地址
     */
    void updateFriendAddress(UUID friendID, InetAddress address);

    /**
     * 获取朋友的 IP 地址
     * @param friendID  朋友 ID
     * @return          朋友 IP
     */
    Optional<InetAddress> getAddress(UUID friendID);
}
