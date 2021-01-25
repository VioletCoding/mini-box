package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbSubMenu;
import com.ghw.minibox.mapper.MbSubMenuMapper;
import com.ghw.minibox.service.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 子菜单实现
 * @date 2021/1/12
 */
@Service
public class SubMenuImpl implements CommonService<MbSubMenu> {
    @Resource
    private MbSubMenuMapper subMenuMapper;

    @Override
    public List<MbSubMenu> selectAll(MbSubMenu param){
        return subMenuMapper.queryAll(param);
    }

    @Override
    public MbSubMenu selectOne(Long id){
        return null;
    }

    @Override
    public Object insert(MbSubMenu entity) {
        return subMenuMapper.saveMenu(entity);
    }

    @Override
    public boolean update(MbSubMenu entity) {
        return subMenuMapper.updateMenu(entity) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return subMenuMapper.deleteMenu(id) > 0;
    }
}
