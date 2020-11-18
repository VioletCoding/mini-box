package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbBlock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (MbBlock)表数据库访问层
 *
 * @author Violet
 * @since 2020-11-19 00:57:02
 */
public interface MbBlockMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param bid 主键
     * @return 实例对象
     */
    MbBlock queryById(Long bid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbBlock> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mbBlock 实例对象
     * @return 对象列表
     */
    List<MbBlock> queryAll(MbBlock mbBlock);

    /**
     * 新增数据
     *
     * @param mbBlock 实例对象
     * @return 影响行数
     */
    int insert(MbBlock mbBlock);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbBlock> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MbBlock> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbBlock> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MbBlock> entities);

    /**
     * 修改数据
     *
     * @param mbBlock 实例对象
     * @return 影响行数
     */
    int update(MbBlock mbBlock);

    /**
     * 通过主键删除数据
     *
     * @param bid 主键
     * @return 影响行数
     */
    int deleteById(Long bid);

}