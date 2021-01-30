package com.ghw.minibox.service;

import java.util.List;

/**
 * @author Violet
 * @description 通用服务接口
 * @date 2021/1/4
 */

public interface CommonService<T>{
    /**
     * 通用列表查找
     */
    List<T> selectAll(T param);

    /**
     * 获取一个
     */
    T selectOne(Long id);

    /**
     * 插入
     */
    boolean insert(T entity);

    /**
     * 更新
     */
    boolean update(T entity);

    /**
     * 删除
     */
    boolean delete(Long id);


}
