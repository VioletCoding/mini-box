package com.ghw.minibox.service;

import com.ghw.minibox.entity.MbPhoto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * (MbPhoto)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 12:20:14
 */
public interface MbPhotoService {

    /**
     * 通过ID查询单条数据
     *
     * @param pid 主键
     * @return 实例对象
     */
    MbPhoto queryById(Long pid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<MbPhoto> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param mbPhoto 实例对象
     * @return 实例对象
     */
    MbPhoto insert(MbPhoto mbPhoto);

    /**
     * 修改数据
     *
     * @param file 文件对象
     * @return 实例对象
     */
    MbPhoto update(MultipartFile file, Long uid) throws IOException;

    /**
     * 通过主键删除数据
     *
     * @param pid 主键
     * @return 是否成功
     */
    boolean deleteById(Long pid);

}