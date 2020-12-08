package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.utils.ResultCode;

import java.util.List;

/**
 * (MbComment)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 12:20:11
 */
public interface MbCommentService {


    /**
     * 发表评论
     *
     * @param mbComment 实例对象
     * @return 实例对象
     */
    ResultCode postComment(MbComment mbComment);


    /**
     * 通过ID查询单条数据
     *
     * @param cid 主键
     * @return 实例对象
     */
    MbComment queryById(Long cid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbComment> queryAllByLimit(int offset, int limit);

    /**
     * 修改数据
     *
     * @param mbComment 实例对象
     * @return 实例对象
     */
    MbComment update(MbComment mbComment);

    /**
     * 通过主键删除数据
     *
     * @param cid 主键
     * @return 是否成功
     */
    boolean deleteById(Long cid);

}