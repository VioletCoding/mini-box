package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbSubMenu;

import java.util.List;

/**
 * @author Violet
 * @description 子菜单Mapper
 * @date 2021/1/12
 */

public interface MbSubMenuMapper {
    /**
     * 根据实例全查
     *
     * @param mbSubMenu 实例
     * @return 菜单列表
     */
    List<MbSubMenu> queryAll(MbSubMenu mbSubMenu);

    /**
     * 新增一个菜单
     *
     * @param mbSubMenu 实例
     * @return 影响行数
     */
    int saveMenu(MbSubMenu mbSubMenu);


    /**
     * 更新菜单信息
     *
     * @param mbSubMenu 实例
     * @return 影响行数
     */
    int updateMenu(MbSubMenu mbSubMenu);


    /**
     * 删除菜单
     *
     * @param menuId 菜单id
     * @return 影响行数
     */
    int deleteMenu(Long menuId);
}
