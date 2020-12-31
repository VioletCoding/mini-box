package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbTag;
import com.ghw.minibox.mapper.MbTagMapper;
import com.ghw.minibox.service.MbTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MbTag)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 12:20:20
 */
@Service
public class MbTagServiceImpl implements MbTagService {
    @Resource
    private MbTagMapper mbTagMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    @Override
    public MbTag queryById(Long tid) {
        return this.mbTagMapper.queryById(tid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MbTag> queryAllByLimit(int offset, int limit) {
        return this.mbTagMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mbTag 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MbTag insert(MbTag mbTag) {
        this.mbTagMapper.insert(mbTag);
        return mbTag;
    }

    /**
     * 修改数据
     *
     * @param mbTag 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MbTag update(MbTag mbTag) {
        this.mbTagMapper.update(mbTag);
        return this.queryById(mbTag.getTid());
    }

    /**
     * 通过主键删除数据
     *
     * @param tid 主键
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long tid) {
        return this.mbTagMapper.deleteById(tid) > 0;
    }
}