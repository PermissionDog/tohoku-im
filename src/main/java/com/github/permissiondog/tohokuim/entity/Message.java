package com.github.permissiondog.tohokuim.entity;

import com.github.permissiondog.tohokuim.entity.enumeration.MessageDirection;

import java.time.LocalDateTime;

public class Message {
    private LocalDateTime sendTime;
    private String message;
    private MessageDirection direction;

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
}
