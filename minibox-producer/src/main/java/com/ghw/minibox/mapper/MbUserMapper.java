package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * @return 影响行数
     */
    int insert(MbUser mbUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MbUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbUser> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MbUser> entities);

    /**
     * 修改数据
     *
     * @param mbUser 实例对象
     * @return 影响行数
     */
    int update(MbUser mbUser);

    /**
     * 通过主键删除数据
     *
     * @param uid 主键
     * @return 影响行数
     */
    int deleteById(Long uid);

}