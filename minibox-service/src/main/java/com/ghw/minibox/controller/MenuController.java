package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbParentMenu;
import com.ghw.minibox.service.impl.ParentMenuImpl;
import com.qiniu.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 统一菜单管理控制层
 * @date 2021/1/12
 */
@RestController
@RequestMapping("menu")
public class MenuController {
    @Resource
    private ParentMenuImpl parentMenu;
    //@Resource
    //private SubMenuImpl subMenu;
    @Resource
    private GenerateResult<Object> gr;

    @ApiOperation("获取菜单列表")
    @GetMapping("all")
    public ReturnDto<Object> showMenu(@RequestParam(value = "id", required = false) Long id,
                                      @RequestParam(value = "menuName", required = false) String menuName) {

        MbParentMenu parentMenu = null;

        if (id != null) parentMenu = new MbParentMenu().setId(id);

        if (!StringUtils.isNullOrEmpty(menuName))
            parentMenu = new MbParentMenu().setMenuName(menuName);

        List<MbParentMenu> menuList = this.parentMenu.selectAll(parentMenu);

        return gr.success(menuList);
    }
}
