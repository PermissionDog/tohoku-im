package com.github.permissiondog.tohokuim.entity;

import java.util.List;
import java.util.UUID;

public class Friend implements Identifiable {
    private UUID uuid;
    private String signature;
    private List<Message> messages;

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}