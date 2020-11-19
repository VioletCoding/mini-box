package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbPhoto;
import com.ghw.minibox.mapper.MbPhotoMapper;
import com.ghw.minibox.service.MbPhotoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MbPhoto)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 12:20:14
 */
@Service
public class MbPhotoServiceImpl implements MbPhotoService {
    @Resource
    private MbPhotoMapper mbPhotoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param pid 主键
     * @return 实例对象
     */
    @Override
    public MbPhoto queryById(Long pid) {
        return this.mbPhotoMapper.queryById(pid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MbPhoto> queryAllByLimit(int offset, int limit) {
        return this.mbPhotoMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mbPhoto 实例对象
     * @return 实例对象
     */
    @Override
    public MbPhoto insert(MbPhoto mbPhoto) {
        this.mbPhotoMapper.insert(mbPhoto);
        return mbPhoto;
    }

    /**
     * 修改数据
     *
     * @param mbPhoto 实例对象
     * @return 实例对象
     */
    @Override
    public MbPhoto update(MbPhoto mbPhoto) {
        this.mbPhotoMapper.update(mbPhoto);
        return this.queryById(mbPhoto.getPid());
    }

    /**
     * 通过主键删除数据
     *
     * @param pid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long pid) {
        return this.mbPhotoMapper.deleteById(pid) > 0;
    }
}