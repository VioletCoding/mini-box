package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbUser;

import java.util.List;

/**
 * (MbUser)表服务接口
 *
 * @author Violet
 * @since 2020-11-18 23:34:57
 */
public interface MbUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param uid 主键
     * @return 实例对象
     */
    MbUser queryById(Long uid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param mbUser 实例对象
     * @return 实例对象
     */
    MbUser insert(MbUser mbUser);

    /**
     * 修改数据
     *
     * @param mbUser 实例对象
     * @return 实例对象
     */
    MbUser update(MbUser mbUser);

    /**
     * 通过主键删除数据
     *
     * @param uid 主键
     * @return 是否成功
     */
    boolean deleteById(Long uid);

}