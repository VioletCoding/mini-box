package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbPost;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (MbPost)表数据库访问层
 *
 * @author Violet
 * @since 2020-11-19 12:20:16
 */
public interface MbPostMapper {


    /**
     * 在首页显示帖子列表，通过PageHelper分页
     *
     * @return 帖子列表
     */
    List<MbPost> showPostList();

    /**
     * 显示帖子详情
     *
     * @param tid 帖子ID
     * @return 帖子内容，包括作者的部分信息
     */
    MbPost showPostDetail(Long tid);


    /**
     * 通过ID查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    MbPost queryById(Long tid);


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
    @Transactional
    int insert(MbPost mbPost);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbPost> 实例对象列表
     * @return 影响行数
     */
    //int insertBatch(@Param("entities") List<MbPost> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbPost> 实例对象列表
     * @return 影响行数
     */
    //int insertOrUpdateBatch(@Param("entities") List<MbPost> entities);

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