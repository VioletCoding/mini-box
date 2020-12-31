package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbRole;
import com.ghw.minibox.mapper.MbRoleMapper;
import com.ghw.minibox.service.MbRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MbRole)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 12:20:18
 */
@Service
public class MbRoleServiceImpl implements MbRoleService {
    @Resource
    private MbRoleMapper mbRoleMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param rid 主键
     * @return 实例对象
     */
    @Override
    public MbRole queryById(Long rid) {
        return this.mbRoleMapper.queryById(rid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MbRole> queryAllByLimit(int offset, int limit) {
        return this.mbRoleMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mbRole 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MbRole insert(MbRole mbRole) {
        this.mbRoleMapper.insert(mbRole);
        return mbRole;
    }

    /**
     * 修改数据
     *
     * @param mbRole 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MbRole update(MbRole mbRole) {
        this.mbRoleMapper.update(mbRole);
        return this.queryById(mbRole.getRid());
    }

    /**
     * 通过主键删除数据
     *
     * @param rid 主键
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long rid) {
        return this.mbRoleMapper.deleteById(rid) > 0;
    }
}