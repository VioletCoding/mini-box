package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbComment;

import java.util.List;

/**
 * (MbComment)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 00:56:50
 */
public interface MbCommentService {

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
     * 新增数据
     *
     * @param mbComment 实例对象
     * @return 实例对象
     */
    MbComment insert(MbComment mbComment);

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