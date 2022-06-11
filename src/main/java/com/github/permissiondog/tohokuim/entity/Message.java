package com.github.permissiondog.tohokuim.entity;

import java.time.LocalDateTime;

public class Message {
    private LocalDateTime sendTime;
    private String message;
    /**
     *  1: 接收的消息
     *  2: 发送的消息
     */
    private int direction;

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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
