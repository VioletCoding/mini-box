package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (MbPost)表数据库访问层
 *
 * @author Violet
 * @since 2021-01-04 19:36:27
 */
public interface MbPostMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MbPost queryById(Long id);

    /**
     * in 查询
     *
     * @param id 帖子id列表
     * @return 帖子列表
     */
    List<MbPost> queryInId(List<Long> id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbPost> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mbPost 实例对象
     * @return 对象列表
     */
    List<MbPost> queryAll(MbPost mbPost);

    /**
     * 新增数据
     *
     * @param mbPost 实例对象
     * @return 影响行数
     */
    int insert(MbPost mbPost);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbPost> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MbPost> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbPost> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MbPost> entities);

    /**
     * 修改数据
     *
     * @param mbPost 实例对象
     * @return 影响行数
     */
    int update(MbPost mbPost);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Object id);

}