package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (MbComment)表数据库访问层
 *
 * @author Violet
 * @since 2020-11-19 12:20:10
 */
public interface MbCommentMapper {

    /**
     * 获取评论条数
     *
     * @return 评论条数
     */
    int countComment();

    /**
     * 通过ID查询单条数据
     *
     * @param cid 主键
     * @return 实例对象
     */
    MbComment queryById(Long cid);

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
     * @param cid 主键
     * @return 影响行数
     */
    int deleteById(Long cid);

}