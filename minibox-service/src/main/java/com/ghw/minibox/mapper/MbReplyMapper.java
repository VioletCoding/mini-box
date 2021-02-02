package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (MbReply)表数据库访问层
 *
 * @author Violet
 * @since 2021-01-04 23:42:48
 */
@Deprecated
public interface MbReplyMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MbReply queryById(Long id);

    /**
     * in查询
     *
     * @param id 主键
     * @return 实例对象
     */
    List<MbReply> queryInId(List<Long> id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbReply> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mbReply 实例对象
     * @return 对象列表
     */
    List<MbReply> queryAll(MbReply mbReply);

    /**
     * 新增数据
     *
     * @param mbReply 实例对象
     * @return 影响行数
     */
    int insert(MbReply mbReply);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbReply> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MbReply> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbReply> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MbReply> entities);

    /**
     * 修改数据
     *
     * @param mbReply 实例对象
     * @return 影响行数
     */
    int update(MbReply mbReply);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Object id);

}