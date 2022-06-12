package com.github.permissiondog.tohokuim.service.exception;

public class NetworkException extends RuntimeException{
    private final String msg;
    public NetworkException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
