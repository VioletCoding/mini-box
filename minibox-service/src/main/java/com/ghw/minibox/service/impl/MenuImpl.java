package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbMenu;
import com.ghw.minibox.mapper.MbMenuMapper;
import com.ghw.minibox.service.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 父菜单实现
 * @date 2021/1/12
 */
@Service
public class MenuImpl implements CommonService<MbMenu> {
    @Resource
    private MbMenuMapper menuMapper;

    @Override
    public List<MbMenu> selectAll(MbMenu param) {
        return menuMapper.queryAll(param);
    }

    @Override
    public MbMenu selectOne(Long id) {
        return menuMapper.queryAll(new MbMenu().setId(id)).get(0);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean insert(MbMenu entity) {
        return menuMapper.saveMenu(entity) > 0;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean update(MbMenu entity) {
        return menuMapper.updateMenu(entity) > 0;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean delete(Long id) {
        return menuMapper.deleteMenu(id) > 0;
    }
}
