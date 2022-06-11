package com.github.permissiondog.tohokuim.entity;


public class Profile {
    // 用户名
    private String name;
    // 个人签名
    private String signature;

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
