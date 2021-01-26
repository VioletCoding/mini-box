package com.ghw.minibox.controller;

import cn.hutool.core.util.IdUtil;
import com.ghw.minibox.component.Result;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.*;
import com.ghw.minibox.feign.SearchFeignClient;
import com.ghw.minibox.mapper.MapperUtils;
import com.ghw.minibox.mapper.MbRoleMapper;
import com.ghw.minibox.service.impl.GameImpl;
import com.ghw.minibox.service.impl.ParentMenuImpl;
import com.ghw.minibox.service.impl.SubMenuImpl;
import com.ghw.minibox.service.impl.UserImpl;
import com.ghw.minibox.utils.QiNiuUtil;
import com.qiniu.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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
    private SubMenuImpl subMenu;
    @Resource
    private MapperUtils mapperUtils;
    @Resource
    private QiNiuUtil qiNiuUtil;
    @Resource
    private MbRoleMapper roleMapper;
    @Resource
    private UserImpl userImpl;
    @Resource
    private GameImpl gameImpl;
    @Resource
    private SearchFeignClient searchFeignClient;

    @ApiOperation("获取菜单列表")
    @GetMapping("allMenu")
    public ReturnDto showMenu(@RequestParam(value = "id", required = false) Long id,
                              @RequestParam(value = "menuName", required = false) String menuName) {
        MbParentMenu parentMenu = new MbParentMenu();
        if (id != null)
            parentMenu.setId(id);
        if (!StringUtils.isNullOrEmpty(menuName))
            parentMenu.setMenuName(menuName);
        List<MbParentMenu> menuList = this.parentMenu.selectAll(parentMenu);
        return Result.success(menuList);
    }

    @ApiOperation("添加父菜单")
    @PostMapping("addParentMenu")
    public ReturnDto addParentMenu(@RequestBody MbParentMenu parentMenu) {
        this.parentMenu.insert(parentMenu);
        return Result.success();
    }

    @ApiOperation("添加子菜单")
    @PostMapping("addSubMenu")
    public ReturnDto addSubMenu(@RequestBody MbSubMenu subMenu) {
        this.subMenu.insert(subMenu);
        return Result.success();
    }

    @ApiOperation("删除父菜单")
    @GetMapping("delParentMenu")
    public ReturnDto delParentMenu(@RequestParam Long id) {
        List<MbParentMenu> parentMenu = this.parentMenu.selectAll(new MbParentMenu().setId(id));
        if (parentMenu.get(0).getSubMenuList().size() > 0)
            return Result.fail("请先删除该菜单下的所有子菜单");
        this.parentMenu.delete(id);
        return Result.success();
    }

    @ApiOperation("删除子菜单")
    @GetMapping("delSubMenu")
    public ReturnDto delSubMenu(@RequestParam Long id) {
        this.subMenu.delete(id);
        return Result.success();
    }

    @ApiOperation("修改父菜单信息")
    @PostMapping("updateParentMenu")
    public ReturnDto updateParentMenu(@RequestBody MbParentMenu parentMenu) {
        boolean update = this.parentMenu.update(parentMenu);
        if (update)
            return Result.success();
        return Result.fail();
    }

    @ApiOperation("修改子菜单信息")
    @PostMapping("updateSubMenu")
    public ReturnDto updateSubMenu(@RequestBody MbSubMenu subMenu) {
        boolean update = this.subMenu.update(subMenu);
        if (update)
            return Result.success();
        return Result.fail();
    }


    @ApiOperation("获取首页的「帖子数量」「用户数量」「游戏数量」「评论数量」以及echarts图表数据")
    @GetMapping("allCount")
    public ReturnDto getCountNumber() {
        Map<String, Object> countMap = mapperUtils.count();
        List<Map<String, Object>> postsPerDay = mapperUtils.echartsPostPerDay();
        List<Map<String, Object>> gameSalesRankings = mapperUtils.gameSalesRankings();
        List<Map<String, Object>> commentPerDay = mapperUtils.echartsCommentPerDay();
        countMap.put("echartsPost", postsPerDay);
        countMap.put("gameSalesRankings", gameSalesRankings);
        countMap.put("commentPerDay", commentPerDay);
        return Result.success(countMap);
    }

    @ApiOperation("获取用户列表里的所有数据")
    @PostMapping("userList")
    public ReturnDto getUserList(@RequestBody(required = false) MbUser mbUser) {
        return Result.success(userImpl.selectAll(mbUser));
    }

    @ApiOperation("更新用户信息")
    @PostMapping("updateUser")
    public ReturnDto updateUserInfo(@RequestBody MbUser mbUser) {
        boolean update = userImpl.update(mbUser);
        if (update)
            return Result.success();
        return Result.fail();
    }

    @ApiOperation("删除用户的管理员角色")
    @GetMapping("deleteRole")
    public ReturnDto updateUserRole(@RequestParam Long userId) {
        int i = mapperUtils.deleteUserAdminRole(userId);
        if (i > 0)
            return Result.success();
        return Result.fail("用户不是管理员，如果要删除USER角色，请直接删除用户");
    }

    @ApiOperation("添加管理员角色")
    @GetMapping("addRole")
    public ReturnDto giveAdminRole(@RequestParam Long userId) {
        List<MbUser> users = userImpl.selectAll(new MbUser().setId(userId));
        MbUser mbUser = users.get(0);
        List<MbRole> roleList = mbUser.getRoleList();
        for (MbRole m : roleList) {
            if (m.getId() == 10001)
                return Result.fail("用户已经是管理员了");
        }
        int i = mapperUtils.setUserRole(10001L, userId);
        if (i > 0)
            return Result.success();
        return Result.fail();
    }

    @ApiOperation("角色列表展示")
    @PostMapping("showRoles")
    public ReturnDto showRoleList(@RequestBody(required = false) MbRole mbRole) {
        return Result.success(roleMapper.queryAll(mbRole));
    }

    @ApiOperation("删除用户")
    @GetMapping("deleteUser")
    public ReturnDto deleteUserById(@RequestParam Long id) {
        return Result.success(userImpl.delete(id));
    }

    @ApiOperation("游戏列表")
    @PostMapping("gameList")
    public ReturnDto getGameList(@RequestBody(required = false) MbGame mbGame) {
        return Result.success(gameImpl.selectAll(mbGame));
    }

    @ApiOperation("上传文件")
    @PostMapping("upload")
    public ReturnDto upload(@RequestParam MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty())
            return Result.fail("文件为空");
        String link = qiNiuUtil.syncUpload(IdUtil.fastSimpleUUID(), multipartFile.getBytes());
        return Result.success(link);
    }

    @ApiOperation("更新游戏信息")
    @PostMapping("updateGameInfo")
    public ReturnDto updateGameInfo(@RequestBody MbGame mbGame) {
        boolean update = gameImpl.update(mbGame);
        if (update)
            return Result.success();
        return Result.fail();
    }

    @ApiOperation("新增游戏")
    @PostMapping("addGame")
    public ReturnDto addNewGame(@RequestBody MbGame mbGame) {
        boolean insert = (boolean) gameImpl.insert(mbGame);
        if (insert) {
            searchFeignClient.refreshData();
            return Result.success();
        }
        return Result.fail();
    }

    @ApiOperation("删除游戏")
    @GetMapping("delGame")
    public ReturnDto deleteGame(@RequestParam Long id){
        boolean delete = gameImpl.delete(id);
        if (delete){
            searchFeignClient.refreshData();
            return Result.success();
        }
        return Result.fail();
    }


}
