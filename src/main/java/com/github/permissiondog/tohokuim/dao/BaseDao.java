package com.github.permissiondog.tohokuim.dao;

import java.lang.reflect.Type;

public interface BaseDao<T> {
    void registerListener(Observer observer);

    // 获取这个 Dao 对应的类型
    Type getType();

    // 对应 JSON 文件的名称
    String getFileName();

    // 从文件加载数据
    void load();

    // 保存数据到文件
    void save();
}
