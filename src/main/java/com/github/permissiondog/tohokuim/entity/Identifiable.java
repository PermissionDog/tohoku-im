package com.github.permissiondog.tohokuim.entity;

import java.util.UUID;

public interface Identifiable {
    UUID getUUID();
    void setUUID(UUID uuid);
}
