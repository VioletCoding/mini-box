package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbTag;

import java.util.List;

/**
 * (MbTag)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 00:57:59
 */
public interface MbTagService {

    /**
     * 通过ID查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    MbTag queryById(Long tid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbTag> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param mbTag 实例对象
     * @return 实例对象
     */
    MbTag insert(MbTag mbTag);

    /**
     * 修改数据
     *
     * @param mbTag 实例对象
     * @return 实例对象
     */
    MbTag update(MbTag mbTag);

    /**
     * 通过主键删除数据
     *
     * @param tid 主键
     * @return 是否成功
     */
    boolean deleteById(Long tid);

}