package com.ghw.minibox.controller;

import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.model.MenuModel;
import com.ghw.minibox.service.MbpMenuService;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 菜单控制器
 * @date 2021/2/5
 */
@RestController
@RequestMapping("menuApi")
public class MbpMenuController {
    @Resource
    private MbpMenuService mbpMenuService;

    /**
     * 增加菜单
     *
     * @param menuModel 实体
     * @return 是否成功
     */
    @PostMapping("add")
    public ResultVo addMenu(@RequestBody @Validated MenuModel menuModel) {
        boolean b = mbpMenuService.addMenu(menuModel);
        return Result.successFlag(b);
    }

    /**
     * 修改菜单
     *
     * @param menuModel 实体
     * @return 新菜单列表
     */
    @PostMapping("modify")
    public ResultVo modifyMenu(@RequestBody MenuModel menuModel) {
        if (menuModel.getId() == null) {
            throw new MiniBoxException("菜单ID不能为空");
        }
        List<MenuModel> models = mbpMenuService.modifyMenu(menuModel);
        return Result.success(models);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单id
     * @return 是否成功
     */
    @GetMapping("delete")
    public ResultVo removeMenu(@RequestParam Long id) {
        boolean b = mbpMenuService.removeMenu(id);
        return Result.successFlag(b);
    }

    /**
     * 查找菜单，条件查询，不传参数返回全部
     *
     * @param menuModel 实体，可以为空
     * @return 菜单列表
     */
    @PostMapping("find")
    public ResultVo menuList(@RequestBody(required = false) MenuModel menuModel) {
        List<MenuModel> menuModels = mbpMenuService.menuModelList(menuModel);
        return Result.success(menuModels);
    }
}
