package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbGame;
import com.ghw.minibox.mapper.MbGameMapper;
import com.ghw.minibox.service.MbGameService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MbGame)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 12:20:12
 */
@Service
public class MbGameServiceImpl implements MbGameService {
    @Resource
    private MbGameMapper mbGameMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param gid 主键
     * @return 实例对象
     */
    @Override
    public MbGame queryById(Long gid) {
        return this.mbGameMapper.queryById(gid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MbGame> queryAllByLimit(int offset, int limit) {
        return this.mbGameMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mbGame 实例对象
     * @return 实例对象
     */
    @Override
    public MbGame insert(MbGame mbGame) {
        this.mbGameMapper.insert(mbGame);
        return mbGame;
    }

    /**
     * 修改数据
     *
     * @param mbGame 实例对象
     * @return 实例对象
     */
    @Override
    public MbGame update(MbGame mbGame) {
        this.mbGameMapper.update(mbGame);
        return this.queryById(mbGame.getGid());
    }

    /**
     * 通过主键删除数据
     *
     * @param gid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long gid) {
        return this.mbGameMapper.deleteById(gid) > 0;
    }
}