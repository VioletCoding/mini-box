package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbComment;
import com.ghw.minibox.mapper.MbCommentMapper;
import com.ghw.minibox.service.MbCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MbComment)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 12:20:11
 */
@Service
public class MbCommentServiceImpl implements MbCommentService {
    @Resource
    private MbCommentMapper mbCommentMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param cid 主键
     * @return 实例对象
     */
    @Override
    public MbComment queryById(Long cid) {
        return this.mbCommentMapper.queryById(cid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MbComment> queryAllByLimit(int offset, int limit) {
        return this.mbCommentMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mbComment 实例对象
     * @return 实例对象
     */
    @Override
    public MbComment insert(MbComment mbComment) {
        this.mbCommentMapper.insert(mbComment);
        return mbComment;
    }

    /**
     * 修改数据
     *
     * @param mbComment 实例对象
     * @return 实例对象
     */
    @Override
    public MbComment update(MbComment mbComment) {
        this.mbCommentMapper.update(mbComment);
        return this.queryById(mbComment.getCid());
    }

    /**
     * 通过主键删除数据
     *
     * @param cid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long cid) {
        return this.mbCommentMapper.deleteById(cid) > 0;
    }
}