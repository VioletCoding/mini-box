package com.ghw.minibox.service.impl;

import com.ghw.minibox.mapper.MbUserDao;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.service.MbUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MbUser)表服务实现类
 *
 * @author Violet
 * @since 2020-11-18 23:34:57
 */
@Service
public class MbUserServiceImpl implements MbUserService {
    @Resource
    private MbUserDao mbUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param uid 主键
     * @return 实例对象
     */
    @Override
    public MbUser queryById(Long uid) {
        return this.mbUserDao.queryById(uid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<MbUser> queryAllByLimit(int offset, int limit) {
        return this.mbUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param mbUser 实例对象
     * @return 实例对象
     */
    @Override
    public MbUser insert(MbUser mbUser) {
        this.mbUserDao.insert(mbUser);
        return mbUser;
    }

    /**
     * 修改数据
     *
     * @param mbUser 实例对象
     * @return 实例对象
     */
    @Override
    public MbUser update(MbUser mbUser) {
        this.mbUserDao.update(mbUser);
        return this.queryById(mbUser.getUid());
    }

    /**
     * 通过主键删除数据
     *
     * @param uid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long uid) {
        return this.mbUserDao.deleteById(uid) > 0;
    }
}