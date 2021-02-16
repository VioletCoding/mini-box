package com.ghw.minibox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.MbpMenuMapper;
import com.ghw.minibox.model.MenuModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 菜单 业务实现
 * @date 2021/2/5
 */
@Service
public class MbpMenuService {
    @Resource
    private MbpMenuMapper mbpMenuMapper;

    /**
     * 条件选择菜单
     *
     * @param menuModel 实体 可以为空
     * @return 菜单列表
     */
    public List<MenuModel> menuModelList(MenuModel menuModel) {
        QueryWrapper<MenuModel> wrapper = new QueryWrapper<>(menuModel);
        return mbpMenuMapper.selectList(wrapper);
    }

    /**
     * 添加菜单
     *
     * @param menuModel 实体
     * @return 是否成功
     */
    @Transactional(rollbackFor = Throwable.class)
    public boolean addMenu(MenuModel menuModel) {
        return mbpMenuMapper.insert(menuModel) > 0;
    }

    /**
     * 更新菜单信息，根据传入的实体更新，字段为null则不更新
     *
     * @param menuModel 实体
     * @return 是否成功
     */
    @Transactional(rollbackFor = Throwable.class)
    public List<MenuModel> modifyMenu(MenuModel menuModel) {
        UpdateWrapper<MenuModel> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", menuModel.getId());
        int update = mbpMenuMapper.update(menuModel, wrapper);
        if (update > 0) {
            MenuModel newData = new MenuModel();
            newData.setId(menuModel.getId());
            return menuModelList(null);
        }
        throw new MiniBoxException("菜单更新失败");
    }

    @Transactional(rollbackFor = Throwable.class)
    public boolean removeMenu(Long menuId) {
        return mbpMenuMapper.deleteById(menuId) > 0;
    }

}
