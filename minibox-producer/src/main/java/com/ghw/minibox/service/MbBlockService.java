package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbBlock;

import java.util.List;

/**
 * (MbBlock)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 12:20:09
 */
public interface MbBlockService {

    /**
     * 通过ID查询单条数据
     *
     * @param bid 主键
     * @return 实例对象
     */
    MbBlock queryById(Long bid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbBlock> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param mbBlock 实例对象
     * @return 实例对象
     */
    MbBlock insert(MbBlock mbBlock);

    /**
     * 修改数据
     *
     * @param mbBlock 实例对象
     * @return 实例对象
     */
    MbBlock update(MbBlock mbBlock);

    /**
     * 通过主键删除数据
     *
     * @param bid 主键
     * @return 是否成功
     */
    boolean deleteById(Long bid);

}