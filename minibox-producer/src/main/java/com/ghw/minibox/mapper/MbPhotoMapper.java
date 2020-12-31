package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbPhoto;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (MbPhoto)表数据库访问层
 *
 * @author Violet
 * @since 2020-11-19 12:20:14
 */
public interface MbPhotoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param pid 主键
     * @return 实例对象
     */
    MbPhoto queryById(Long pid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbPhoto> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mbPhoto 实例对象
     * @return 对象列表
     */
    List<MbPhoto> queryAll(MbPhoto mbPhoto);

    /**
     * 新增数据
     *
     * @param mbPhoto 实例对象
     * @return 影响行数
     */
    int insert(MbPhoto mbPhoto);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param list List<MbPhoto> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("list") List<MbPhoto> list);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MbPhoto> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<MbPhoto> entities);

    /**
     * 修改数据
     *
     * @param mbPhoto 实例对象
     * @return 影响行数
     */
    int update(MbPhoto mbPhoto);

    /**
     * 通过主键删除数据
     *
     * @param pid 主键
     * @return 影响行数
     */
    int deleteById(Long pid);

}