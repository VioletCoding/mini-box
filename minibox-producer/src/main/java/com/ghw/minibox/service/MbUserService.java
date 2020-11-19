package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbUser;

import java.util.List;

/**
 * (MbUser)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 12:20:22
 */
public interface MbUserService {

    /**
     * 注册
     *
     * @param user 实体
     * @return 创建后的实体
     */
    boolean register(MbUser user);


    /**
     * 通过ID查询单条数据
     *
     * @param uid 主键
     * @return 实例对象
     */
    MbUser queryById(Long uid);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param mbUser 实例对象
     * @return 对象列表
     */
    List<MbUser> queryAll(MbUser mbUser);

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