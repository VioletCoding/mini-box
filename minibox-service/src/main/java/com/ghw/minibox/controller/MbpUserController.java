package com.ghw.minibox.controller;

import com.ghw.minibox.model.CommentModel;
import com.ghw.minibox.model.PostModel;
import com.ghw.minibox.model.UserModel;
import com.ghw.minibox.service.impl.MbpUserServiceImpl;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 用户 控制层
 * @date 2021/1/31
 */
@RestController
@RequestMapping("userApi")
public class MbpUserController {
    @Resource
    private MbpUserServiceImpl userService;

    /**
     * 用户个人信息
     *
     * @param id 用户id
     * @return 用户个人信息
     */
    @GetMapping("userInfo")
    public ResultVo userInfo(@RequestParam Long id) {
        UserModel userModel = userService.findUserDetail(id);
        return Result.success(userModel);
    }

    /**
     * 用户信息修改
     *
     * @param userModel 实体
     * @return 修改后的用户信息
     */
    @PostMapping("userModify")
    public ResultVo userModify(@RequestBody UserModel userModel) {
        UserModel updateUserInfo = userService.updateUserInfo(userModel);
        return Result.success(updateUserInfo);
    }

    /**
     * 查询该用户发表过的所有帖子
     *
     * @param id 用户id
     * @return 用户发表过的所有帖子的详细信息
     */
    @GetMapping("userPosts")
    public ResultVo userPosts(@RequestParam Long id) {
        List<PostModel> userAllPost = userService.findUserAllPost(id);
        return Result.success(userAllPost);
    }

    /**
     * 查询该用户发表过的所有评论
     *
     * @param id 用户id
     * @return 用户发表过的所有评论，以及评论在哪个地方发表的
     */
    @GetMapping("userComments")
    public ResultVo userComments(@RequestParam Long id) {
        List<CommentModel> userAllComment = userService.findUserAllComment(id);
        return Result.success(userAllComment);
    }

    /**
     * 修改用户的密码
     *
     * @param userModel 实体，传id和password
     * @return 是否成功
     */
    @PostMapping("userPwdModify")
    public ResultVo userPwdModify(@RequestBody UserModel userModel) {
        boolean b = userService.updatePassword(userModel.getId(), userModel.getPassword());
        return Result.successFlag(b);
    }

}
