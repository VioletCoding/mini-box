package com.ghw.minibox.service;

import java.util.List;

/**
 * @author Violet
 * @description 服务 基类
 * @date 2021/1/31
 */

public interface BaseService<T> {

    boolean save(T model);

    boolean modify(T model);

    boolean remove(Long id);

    T findOneById(Long id);

    T findOne(String column,Object value);

    List<T> findByModel(T model);
}
