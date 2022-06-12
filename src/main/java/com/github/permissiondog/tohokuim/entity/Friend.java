package com.github.permissiondog.tohokuim.entity;

import java.util.List;
import java.util.UUID;

public class Friend implements Identifiable {
    private UUID uuid;
    private String name;
    private String signature;

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
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

}
