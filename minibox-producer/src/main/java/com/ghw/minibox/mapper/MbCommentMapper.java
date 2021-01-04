package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.entity.MbReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (MbComment)表数据库访问层
 *
 * @author Violet
 * @since 2020-11-19 12:20:10
 */
public interface MbCommentMapper {


    List<MbComment> getAll(MbComment mbComment);

    MbComment getOne(MbComment mbComment);





    //**************************重构

    /**
     * 新增数据
     *
     * @param mbComment 实例对象
     * @return 影响行数
     */
    int insert(MbComment mbComment);


    /**
     * 新增数据到mb_reply表
     *
     * @param mbReply 实体
     * @return 影响行数
     */
    int insertToMbReply(MbReply mbReply);

    /**
     * 通过ID查询单条数据
     *
     * @param cid 主键
     * @return 实例对象
     */
    MbComment queryById(Long cid);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param mbComment 实例对象
     * @return 对象列表
     */
    List<MbComment> queryAll(MbComment mbComment);

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