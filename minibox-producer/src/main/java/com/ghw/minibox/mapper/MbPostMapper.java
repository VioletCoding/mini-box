package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbPost;

import java.util.List;

/**
 * (MbPost)表数据库访问层
 *
 * @author Violet
 * @since 2020-11-19 12:20:16
 */
public interface MbPostMapper {


    /**
     * 通过实体作为筛选条件查询
     * @param mbPost 实例对象
     * @return 对象列表
     */
    List<MbPost> getAll(MbPost mbPost);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param mbPost 实例对象
     * @return 对象列表
     */
    List<MbPost> queryAll(MbPost mbPost);

    /**
     * 显示用户的所有评论在哪些帖子
     *
     * @param uid 用户id
     * @return 信息
     */
    List<MbPost> queryUserAllCommentInPost(Long uid);


    /**
     * 新增数据
     *
     * @param mbPost 实例对象
     * @return 影响行数
     */
    int insert(MbPost mbPost);


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
     * @param tid 主键
     * @return 影响行数
     */
    int deleteById(Long tid);

}