package com.github.permissiondog.tohokuim.entity.enumeration;

import java.util.Arrays;

public enum MessageDirection {
    Sent {
        @Override
        public String stringValue() {
            return "sent";
        }

        @Override
        public String toString() {
            return "发送";
        }
    },
    Received {
        @Override
        public String stringValue() {
            return "received";
        }

        @Override
        public String toString() {
            return "接收";
        }
    };
    public abstract String stringValue();
    public static MessageDirection of(String v) {
        return Arrays.stream(MessageDirection.values()).filter(obj -> obj.stringValue().equals(v)).findAny().orElseThrow();
    }

}
