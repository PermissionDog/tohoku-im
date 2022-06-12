package com.github.permissiondog.tohokuim.net.event;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReceiveMessageEvent implements Event {
    public static final String NAME = "ReceiveMessageEvent";
    private UUID uuid;
    private String message;
    private UUID sender;
    private LocalDateTime sendTime;

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getSender() {
        return sender;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
