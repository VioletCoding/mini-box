package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbGame;

import java.util.List;

/**
 * (MbGame)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 12:20:12
 */
public interface MbGameService {

    /**
     * 通过ID查询单条数据
     *
     * @param gid 主键
     * @return 实例对象
     */
    MbGame queryById(Long gid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbGame> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param mbGame 实例对象
     * @return 实例对象
     */
    MbGame insert(MbGame mbGame);

    /**
     * 修改数据
     *
     * @param mbGame 实例对象
     * @return 实例对象
     */
    MbGame update(MbGame mbGame);

    /**
     * 通过主键删除数据
     *
     * @param gid 主键
     * @return 是否成功
     */
    boolean deleteById(Long gid);

}