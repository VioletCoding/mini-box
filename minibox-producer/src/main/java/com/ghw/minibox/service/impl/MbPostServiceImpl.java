package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.mapper.MbPostMapper;
import com.ghw.minibox.service.MbPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MbPost)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 12:20:16
 */
@Service
public class MbPostServiceImpl implements MbPostService {
    @Resource
    private MbPostMapper mbPostMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    @Override
    public MbPost queryById(Long tid) {
        return this.mbPostMapper.queryById(tid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MbPost> queryAllByLimit(int offset, int limit) {
        return this.mbPostMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mbPost 实例对象
     * @return 实例对象
     */
    @Override
    public MbPost insert(MbPost mbPost) {
        this.mbPostMapper.insert(mbPost);
        return mbPost;
    }

    /**
     * 修改数据
     *
     * @param mbPost 实例对象
     * @return 实例对象
     */
    @Override
    public MbPost update(MbPost mbPost) {
        this.mbPostMapper.update(mbPost);
        return this.queryById(mbPost.getTid());
    }

    /**
     * 通过主键删除数据
     *
     * @param tid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long tid) {
        return this.mbPostMapper.deleteById(tid) > 0;
    }
}