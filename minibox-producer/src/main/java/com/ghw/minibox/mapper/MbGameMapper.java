package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbGame;
import org.apache.ibatis.annotations.Param;

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
     * 新增数据
     *
     * @param mbGame 实例对象
     * @return 影响行数
     */
    int insert(MbGame mbGame);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbGame> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MbGame> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbGame> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MbGame> entities);

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