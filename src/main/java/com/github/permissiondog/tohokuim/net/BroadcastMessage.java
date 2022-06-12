package com.github.permissiondog.tohokuim.net;

import java.net.InetAddress;
import java.util.UUID;

public class BroadcastMessage {
    private InetAddress ip;
    private String name;
    private String signature;
    private UUID uuid;

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
}
