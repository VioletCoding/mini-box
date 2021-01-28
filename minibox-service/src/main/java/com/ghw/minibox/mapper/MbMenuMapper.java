package com.ghw.minibox.mapper;

import com.ghw.minibox.entity.MbMenu;

import java.util.List;

/**
 * @author Violet
 * @description 菜单mapper
 * @date 2021/1/12
 */

public interface MbMenuMapper {
    /**
     * 根据实例全查
     *
     * @param mbMenu 实例
     * @return 菜单列表
     */
    List<MbMenu> queryAll(MbMenu mbMenu);

    /**
     * 新增一个菜单
     *
     * @param mbMenu 实例
     * @return 影响行数
     */
    int saveMenu(MbMenu mbMenu);


    /**
     * 更新菜单信息
     *
     * @param mbMenu 实例
     * @return 影响行数
     */
    int updateMenu(MbMenu mbMenu);


    /**
     * 删除父菜单，那么关联的子菜单也要被删除
     *
     * @param menuId 菜单id
     * @return 影响行数
     */
    int deleteMenu(Long menuId);


}
