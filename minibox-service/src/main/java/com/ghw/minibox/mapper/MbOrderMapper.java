package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (MbOrder)表数据库访问层
 *
 * @author Violet
 * @since 2021-01-09 20:23:22
 */
@Deprecated
public interface MbOrderMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MbOrder queryById(Object id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbOrder> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mbOrder 实例对象
     * @return 对象列表
     */
    List<MbOrder> queryAll(MbOrder mbOrder);

    /**
     * 新增数据
     *
     * @param mbOrder 实例对象
     * @return 影响行数
     */
    int insert(MbOrder mbOrder);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbOrder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MbOrder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbOrder> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MbOrder> entities);

    /**
     * 修改数据
     *
     * @param mbOrder 实例对象
     * @return 影响行数
     */
    int update(MbOrder mbOrder);

    /**
     * 通过主键删除数据
     *
     * @param mbOrder 实例
     * @return 影响行数
     */
    int deleteById(MbOrder mbOrder);

}