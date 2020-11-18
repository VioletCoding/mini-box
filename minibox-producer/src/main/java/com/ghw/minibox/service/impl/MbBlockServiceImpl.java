package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbBlock;
import com.ghw.minibox.mapper.MbBlockMapper;
import com.ghw.minibox.service.MbBlockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MbBlock)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 00:57:03
 */
@Service
public class MbBlockServiceImpl implements MbBlockService {
    @Resource
    private MbBlockMapper mbBlockMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param bid 主键
     * @return 实例对象
     */
    @Override
    public MbBlock queryById(Long bid) {
        return this.mbBlockMapper.queryById(bid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MbBlock> queryAllByLimit(int offset, int limit) {
        return this.mbBlockMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mbBlock 实例对象
     * @return 实例对象
     */
    @Override
    public MbBlock insert(MbBlock mbBlock) {
        this.mbBlockMapper.insert(mbBlock);
        return mbBlock;
    }

    /**
     * 修改数据
     *
     * @param mbBlock 实例对象
     * @return 实例对象
     */
    @Override
    public MbBlock update(MbBlock mbBlock) {
        this.mbBlockMapper.update(mbBlock);
        return this.queryById(mbBlock.getBid());
    }

    /**
     * 通过主键删除数据
     *
     * @param bid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long bid) {
        return this.mbBlockMapper.deleteById(bid) > 0;
    }
}