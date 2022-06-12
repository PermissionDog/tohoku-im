package com.github.permissiondog.tohokuim.service;

import com.github.permissiondog.tohokuim.entity.Identifiable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface MultiDataService<T extends Identifiable> extends BaseService<T> {
    /**
     * 根据 ID 获取单条数据
     * @param id    数据ID
     * @return      数据
     */
    Optional<T> get(UUID id);
    /**
     * 更新数据
     * @param value 要更新的数据值
     * @return      如果数据 ID 不存在返回 false, 否则返回 true
     */
    boolean update(T value);

    /**
     * 删除数据
     * @param id    数据ID
     * @return      数据不存在返回 false
     */
    boolean remove(UUID id);

    /**
     * 增加数据, 会自动生成UUID
     * @param value 要增加的值
     * @return      插入的数据
     */
    T add(T value);

    /**
     * 获取全部数据
     * @return  全部数据
     */
    List<T> getAll();

    /**
     * 注册插入数据时的监听器
     * @param listener  监听器
     */
    void registerOnAddListener(Consumer<T> listener);
}
