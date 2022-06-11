package com.github.permissiondog.tohokuim.dao.impl;

import com.github.permissiondog.tohokuim.dao.BaseDao;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    protected BaseDaoImpl() {
        load();
    }


}
