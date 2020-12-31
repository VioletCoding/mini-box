package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbUser;

/**
 * (MbUser)表数据库访问层
 *
 * @author Violet
 * @since 2020-11-19 12:20:21
 */
public interface MbUserMapper {

    /**
     * 通过username来搜索
     *
     * @param username 邮箱
     * @return 实体
     */
    MbUser queryByUsername(String username);

    /**
     * 通过ID查询单条数据
     *
     * @param uid 主键
     * @return 实例对象
     */
    MbUser queryById(Long uid);


    /**
     * 新增数据
     *
     * @param mbUser 实例对象
     * @return 影响行数
     */
    int insert(MbUser mbUser);

    /**
     * 更新数据
     *
     * @param mbUser 实例对象
     * @return 影响行数
     */
    int update(MbUser mbUser);
}