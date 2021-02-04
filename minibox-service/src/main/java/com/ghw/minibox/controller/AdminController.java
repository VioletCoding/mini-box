//package com.ghw.minibox.controller;
//
//import cn.hutool.core.util.IdUtil;
//import com.ghw.minibox.utils.Result;
//import com.ghw.minibox.vo.ResultVo;
//import com.ghw.minibox.entity.*;
//import com.ghw.minibox.feign.SearchFeignClient;
//import com.ghw.minibox.mapper.MapperUtils;
//import com.ghw.minibox.mapper.MbRoleMapper;
//import com.ghw.minibox.service.impl.*;
//import com.ghw.minibox.component.QiNiuUtil;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author Violet
// * @description 管理员页面专用controller
// * @date 2021/1/15
// */
//@RestController
//@RequestMapping("admin")
//public class AdminController {
//    @Resource
//    private MenuImpl menuImpl;
//    @Resource
//    private MapperUtils mapperUtils;
//    @Resource
//    private QiNiuUtil qiNiuUtil;
//    @Resource
//    private MbRoleMapper roleMapper;
//    @Resource
//    private UserImpl userImpl;
//    @Resource
//    private GameImpl gameImpl;
//    @Resource
//    private PostImpl postImpl;
//    @Resource
//    private BlockImpl blockImpl;
//    @Resource
//    private SearchFeignClient searchFeignClient;
//
//    @ApiOperation("获取菜单列表")
//    @PostMapping("allMenu")
//    public ResultVo showMenu(@RequestBody(required = false) MbMenu mbMenu) {
//        List<MbMenu> menuList = this.menuImpl.selectAll(mbMenu);
//        return Result.success(menuList);
//    }
//
//    @ApiOperation("添加菜单")
//    @PostMapping("addMenu")
//    public ResultVo addParentMenu(@RequestBody MbMenu mbMenu) {
//        boolean insert = this.menuImpl.insert(mbMenu);
//        return Result.successFlag(insert);
//    }
//
//    @ApiOperation("删除菜单")
//    @GetMapping("delMenu")
//    public ResultVo delParentMenu(@RequestParam Long id) {
//        boolean delete = this.menuImpl.delete(id);
//        return Result.successFlag(delete);
//    }
//
//    @ApiOperation("修改菜单信息")
//    @PostMapping("updateMenu")
//    public ResultVo updateParentMenu(@RequestBody MbMenu parentMenu) {
//        boolean update = this.menuImpl.update(parentMenu);
//        return Result.successFlag(update);
//    }
//
//
//    @ApiOperation("获取首页的「帖子数量」「用户数量」「游戏数量」「评论数量」以及echarts图表数据")
//    @GetMapping("allCount")
//    public ResultVo getCountNumber() {
//        Map<String, Object> countMap = mapperUtils.count();
//        List<Map<String, Object>> postsPerDay = mapperUtils.echartsPostPerDay();
//        List<Map<String, Object>> gameSalesRankings = mapperUtils.gameSalesRankings();
//        List<Map<String, Object>> commentPerDay = mapperUtils.echartsCommentPerDay();
//        countMap.put("echartsPost", postsPerDay);
//        countMap.put("gameSalesRankings", gameSalesRankings);
//        countMap.put("commentPerDay", commentPerDay);
//        return Result.success(countMap);
//    }
//
//    @ApiOperation("获取用户列表里的所有数据")
//    @PostMapping("userList")
//    public ResultVo getUserList(@RequestBody(required = false) MbUser mbUser) {
//        return Result.success(userImpl.selectAll(mbUser));
//    }
//
//    @ApiOperation("更新用户信息")
//    @PostMapping("updateUser")
//    public ResultVo updateUserInfo(@RequestBody MbUser mbUser) {
//        boolean update = userImpl.update(mbUser);
//        return Result.successFlag(update);
//    }
//
//    @ApiOperation("删除用户的管理员角色")
//    @GetMapping("deleteRole")
//    public ResultVo updateUserRole(@RequestParam Long userId) {
//        int i = mapperUtils.deleteUserAdminRole(userId);
//        return Result.successFlag(i > 0, "用户不是管理员，如果要删除USER角色，请直接删除用户");
//    }
//
//    @ApiOperation("添加管理员角色")
//    @GetMapping("addRole")
//    public ResultVo giveAdminRole(@RequestParam Long userId) {
//        List<MbUser> users = userImpl.selectAll(new MbUser().setId(userId));
//        MbUser mbUser = users.get(0);
//        List<MbRole> roleList = mbUser.getRoleList();
//        for (MbRole m : roleList) {
//            if (m.getId() == 10001)
//                return Result.fail("用户已经是管理员了");
//        }
//        int i = mapperUtils.setUserRole(10001L, userId);
//        return Result.successFlag(i > 0);
//    }
//
//    @ApiOperation("角色列表展示")
//    @PostMapping("showRoles")
//    public ResultVo showRoleList(@RequestBody(required = false) MbRole mbRole) {
//        return Result.success(roleMapper.queryAll(mbRole));
//    }
//
//    @ApiOperation("删除用户")
//    @GetMapping("deleteUser")
//    public ResultVo deleteUserById(@RequestParam Long id) {
//        return Result.success(userImpl.delete(id));
//    }
//
//    @ApiOperation("游戏列表")
//    @PostMapping("gameList")
//    public ResultVo getGameList(@RequestBody(required = false) MbGame mbGame) {
//        return Result.success(gameImpl.selectAll(mbGame));
//    }
//
//    @ApiOperation("上传文件")
//    @PostMapping("upload")
//    public ResultVo upload(@RequestParam MultipartFile multipartFile) throws IOException {
//        if (multipartFile.isEmpty())
//            return Result.fail("文件为空");
//        String link = qiNiuUtil.syncUpload(IdUtil.fastSimpleUUID() + multipartFile.getOriginalFilename(), multipartFile.getBytes());
//        System.out.println("上传完毕,link=>" + link);
//        return Result.success((Object) link);
//    }
//
//    @ApiOperation("更新游戏信息")
//    @PostMapping("updateGameInfo")
//    public ResultVo updateGameInfo(@RequestBody MbGame mbGame) {
//        boolean update = gameImpl.update(mbGame);
//        return Result.successFlag(update);
//    }
//
//    @ApiOperation("新增游戏")
//    @PostMapping("addGame")
//    public ResultVo addNewGame(@RequestBody MbGame mbGame) {
//        boolean insert = gameImpl.insert(mbGame);
//        if (insert) {
//            searchFeignClient.refreshData();
//            return Result.success();
//        }
//        return Result.fail();
//    }
//
//    @ApiOperation("删除游戏")
//    @GetMapping("delGame")
//    public ResultVo deleteGame(@RequestParam Long id) {
//        boolean delete = gameImpl.delete(id);
//        if (delete) {
//            searchFeignClient.refreshData();
//            return Result.success();
//        }
//        return Result.fail();
//    }
//
//    @ApiOperation("帖子列表")
//    @PostMapping("postList")
//    public ResultVo postList(@RequestBody(required = false) MbPost mbPost) {
//        List<MbPost> posts = postImpl.selectAll(mbPost);
//        return Result.success(posts);
//    }
//
//    @ApiOperation("修改帖子")
//    @PostMapping("modifyPost")
//    public ResultVo modifyPost(@RequestBody MbPost mbPost) {
//        boolean update = postImpl.update(mbPost);
//        if (update) {
//            searchFeignClient.refreshData();
//            return Result.success();
//        }
//        return Result.fail();
//    }
//
//    @ApiOperation("删除帖子")
//    @GetMapping("delPost")
//    public ResultVo delPost(@RequestParam Long id) {
//        boolean delete = postImpl.delete(id);
//        if (delete) {
//            searchFeignClient.refreshData();
//            return Result.success();
//        }
//        return Result.fail();
//    }
//
//    @ApiOperation("获取版块信息")
//    @PostMapping("getBlock")
//    public ResultVo getBlock(@RequestBody(required = false) MbBlock mbBlock) {
//        List<MbBlock> blocks = blockImpl.selectAll(mbBlock);
//        return Result.success(blocks);
//    }
//
//    @ApiOperation("增加版块")
//    @PostMapping("addBlock")
//    public ResultVo addBlock(@RequestBody @Validated MbBlock mbBlock) {
//        MbGame mbGame = gameImpl.selectOne(mbBlock.getGid());
//        if (mbGame == null)
//            return Result.fail("没有找到对应的游戏");
//        boolean insert = blockImpl.insert(mbBlock);
//        return Result.successFlag(insert);
//    }
//
//    @ApiOperation("更新版块信息")
//    @PostMapping("updateBlock")
//    public ResultVo updateBlock(@RequestBody MbBlock mbBlock) {
//        boolean update = blockImpl.update(mbBlock);
//        return Result.successFlag(update);
//    }
//
//    @ApiOperation("删除版块")
//    @GetMapping("delBlock")
//    public ResultVo delBlock(@RequestParam Long id) {
//        MbBlock mbBlock = blockImpl.selectOne(id);
//        if (mbBlock == null)
//            return Result.fail("没有找到该版块");
//        List<MbGame> mbGames = gameImpl.selectAll(new MbGame().setId(mbBlock.getGid()));
//        System.out.println(mbGames.toString());
//        if (mbGames.size() > 0)
//            return Result.fail("该版块下有关联的游戏,请先删除所有关联的游戏再删除该版块");
//        List<MbPost> posts = postImpl.selectAll(new MbPost().setBid(mbBlock.getId()));
//        System.out.println(posts.toString());
//        if (posts.size() > 0)
//            return Result.fail("该版块有关联的帖子，请先删除所有关联的帖子再删除该版块");
//        boolean delete = blockImpl.delete(id);
//        return Result.successFlag(delete);
//    }
//
//}
