package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbParentMenu;
import com.ghw.minibox.entity.MbRole;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.mapper.MapperUtils;
import com.ghw.minibox.service.impl.ParentMenuImpl;
import com.ghw.minibox.service.impl.UserImpl;
import com.qiniu.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 管理员页面专用controller
 * @date 2021/1/15
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    @Resource
    private ParentMenuImpl parentMenu;
    @Resource
    private MapperUtils mapperUtils;
    @Resource
    private UserImpl userImpl;
    @Resource
    private GenerateResult<Object> gr;

    @ApiOperation("获取菜单列表")
    @GetMapping("allMenu")
    public ReturnDto<Object> showMenu(@RequestParam(value = "id", required = false) Long id,
                                      @RequestParam(value = "menuName", required = false) String menuName) {
        MbParentMenu parentMenu = null;
        if (id != null) parentMenu = new MbParentMenu().setId(id);
        if (!StringUtils.isNullOrEmpty(menuName))
            parentMenu = new MbParentMenu().setMenuName(menuName);
        List<MbParentMenu> menuList = this.parentMenu.selectAll(parentMenu);
        return gr.success(menuList);
    }


    @ApiOperation("获取首页的「帖子数量」「用户数量」「游戏数量」「评论数量」以及echarts图表数据")
    @GetMapping("allCount")
    public ReturnDto<Object> getCountNumber() {
        Map<String, Object> countMap = mapperUtils.count();
        List<Map<String, Object>> postsPerDay = mapperUtils.echartsPostPerDay();
        List<Map<String, Object>> gameSalesRankings = mapperUtils.gameSalesRankings();
        List<Map<String, Object>> commentPerDay = mapperUtils.echartsCommentPerDay();
        countMap.put("echartsPost", postsPerDay);
        countMap.put("gameSalesRankings", gameSalesRankings);
        countMap.put("commentPerDay", commentPerDay);
        return gr.success(countMap);
    }

    @ApiOperation("获取用户列表里的所有数据")
    @PostMapping("userList")
    public ReturnDto<Object> getUserList(@RequestBody(required = false) MbUser mbUser) {
        return gr.success(userImpl.selectAll(mbUser));
    }

    @ApiOperation("更新用户信息")
    @PostMapping("updateUser")
    public ReturnDto<Object> updateUserInfo(@RequestBody MbUser mbUser) {
        boolean update = userImpl.update(mbUser);
        if (update) return gr.success();
        return gr.fail();
    }

    @ApiOperation("删除用户的管理员角色")
    @GetMapping("deleteRole")
    public ReturnDto<Object> updateUserRole(@RequestParam Long userId) {
        int i = mapperUtils.deleteUserAdminRole(userId);
        if (i > 0) return gr.success();
        return gr.fail("用户不是管理员，如果要删除USER角色，请直接删除用户");
    }

    @ApiOperation("添加管理员角色")
    @GetMapping("addRole")
    public ReturnDto<Object> giveAdminRole(@RequestParam Long userId) {
        List<MbUser> users = userImpl.selectAll(new MbUser().setId(userId));
        MbUser mbUser = users.get(0);
        List<MbRole> roleList = mbUser.getRoleList();
        for (MbRole m : roleList){
            if (m.getName().equals("ADMIN")) return gr.fail("用户已经是管理员了");
        }
        int i = mapperUtils.setUserRole(10001L, userId);
        if (i > 0) return gr.success();
        return gr.fail();
    }

    @ApiOperation("删除用户")
    @GetMapping("deleteUser")
    public ReturnDto<Object> deleteUserById(@RequestParam Long id) {
        return gr.success(userImpl.delete(id));
    }

}
