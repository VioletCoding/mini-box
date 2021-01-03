package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbGame;

import java.util.List;

/**
 * (MbGame)表数据库访问层
 *
 * @author Violet
 * @since 2020-11-19 12:20:12
 */
public interface MbGameMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param gid 主键
     * @return 实例对象
     */
    MbGame queryById(Long gid);


    /**
     * 全查
     *
     * @return 对象列表
     */
    List<MbGame> queryAll();

    /**
     * 修改数据
     *
     * @param mbGame 实例对象
     * @return 影响行数
     */
    int update(MbGame mbGame);

    /**
     * 通过主键删除数据
     *
     * @param gid 主键
     * @return 影响行数
     */
    int deleteById(Long gid);

}