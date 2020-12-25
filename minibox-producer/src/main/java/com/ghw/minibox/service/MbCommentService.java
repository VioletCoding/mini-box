package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.entity.MbReply;
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
     * 发表回复
     *
     * @param mbReply 实体
     * @return 枚举响应体，成功返回OK
     */
    ResultCode postReply(MbReply mbReply);


    /**
     * 根据传入的参数查询
     *
     * @param mbComment 实体
     * @return 实体
     */
    List<MbComment> queryByEntity(MbComment mbComment);


    /**
     * 通过ID查询单条数据
     *
     * @param cid 主键
     * @return 实例对象
     */
    MbComment queryById(Long cid);

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