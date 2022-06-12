package com.github.permissiondog.tohokuim.entity;

import com.github.permissiondog.tohokuim.entity.enumeration.MessageDirection;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message implements Identifiable {
    private LocalDateTime sendTime;
    private String message;
    private MessageDirection direction;
    private UUID session;
    private UUID uuid;

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageDirection getDirection() {
        return direction;
    }

    public void setDirection(MessageDirection direction) {
        this.direction = direction;
    }

    public UUID getSession() {
        return session;
    }

    public void setSession(UUID session) {
        this.session = session;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
}
