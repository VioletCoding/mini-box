package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbRole;

import java.util.List;

/**
 * (MbRole)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 12:20:18
 */
public interface MbRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param rid 主键
     * @return 实例对象
     */
    MbRole queryById(Long rid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbRole> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param mbRole 实例对象
     * @return 实例对象
     */
    MbRole insert(MbRole mbRole);

    /**
     * 修改数据
     *
     * @param mbRole 实例对象
     * @return 实例对象
     */
    MbRole update(MbRole mbRole);

    /**
     * 通过主键删除数据
     *
     * @param rid 主键
     * @return 是否成功
     */
    boolean deleteById(Long rid);

}