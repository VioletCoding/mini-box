package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbPost;

import java.util.List;

/**
 * (MbPost)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 12:20:16
 */
public interface MbPostService {

    /**
     * 通过ID查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    MbPost queryById(Long tid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbPost> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param mbPost 实例对象
     * @return 实例对象
     */
    MbPost insert(MbPost mbPost);

    /**
     * 修改数据
     *
     * @param mbPost 实例对象
     * @return 实例对象
     */
    MbPost update(MbPost mbPost);

    /**
     * 通过主键删除数据
     *
     * @param tid 主键
     * @return 是否成功
     */
    boolean deleteById(Long tid);

}