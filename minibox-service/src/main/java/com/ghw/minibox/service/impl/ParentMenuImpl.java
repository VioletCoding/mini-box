package com.ghw.minibox.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.entity.MbParentMenu;
import com.ghw.minibox.mapper.MbParentMenuMapper;
import com.ghw.minibox.service.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 父菜单实现
 * @date 2021/1/12
 */
@Service
public class ParentMenuImpl implements CommonService<MbParentMenu> {
    @Resource
    private MbParentMenuMapper parentMenuMapper;

    @Override
    public List<MbParentMenu> selectAll(MbParentMenu param){
        return parentMenuMapper.queryAll(param);
    }

    @Override
    public MbParentMenu selectOne(Long id) throws JsonProcessingException {
        return null;
    }

    @Override
    public Object insert(MbParentMenu entity) throws JsonProcessingException {
        return parentMenuMapper.saveMenu(entity);
    }

    @Override
    public boolean update(MbParentMenu entity) {
        return parentMenuMapper.updateMenu(entity) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return parentMenuMapper.deleteMenu(id) > 0;
    }
}
