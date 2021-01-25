package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (MbComment)表数据库访问层
 *
 * @author Violet
 * @since 2021-01-04 22:15:40
 */
public interface MbCommentMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MbComment queryById(Object id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbComment> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mbComment 实例对象
     * @return 对象列表
     */
    List<MbComment> queryAll(MbComment mbComment);

    /**
     * 找出所有评论，以及每条评论的回复
     *
     * @param mbComment 实体
     * @return 对象列表
     */
    List<MbComment> queryAllWithReply(MbComment mbComment);

    /**
     * 新增数据
     *
     * @param mbComment 实例对象
     * @return 影响行数
     */
    int insert(MbComment mbComment);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbComment> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MbComment> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbComment> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MbComment> entities);

    /**
     * 修改数据
     *
     * @param mbComment 实例对象
     * @return 影响行数
     */
    int update(MbComment mbComment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Object id);

}