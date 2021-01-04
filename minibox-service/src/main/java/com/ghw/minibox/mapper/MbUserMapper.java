package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (MbUser)表数据库访问层
 *
 * @author Violet
 * @since 2021-01-04 22:08:17
 */
public interface MbUserMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MbUser queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


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
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Object id);

}