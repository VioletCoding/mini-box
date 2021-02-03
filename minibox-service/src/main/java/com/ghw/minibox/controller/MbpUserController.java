package com.ghw.minibox.controller;

import com.ghw.minibox.model.CommentModel;
import com.ghw.minibox.model.PostModel;
import com.ghw.minibox.model.UserModel;
import com.ghw.minibox.service.impl.MbpUserServiceImpl;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description
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

    @GetMapping("userPosts")
    public ResultVo userPosts(@RequestParam Long id) {
        List<PostModel> userAllPost = userService.findUserAllPost(id);
        return Result.success(userAllPost);
    }

    @GetMapping("userComments")
    public ResultVo userComments(@RequestParam Long id) {
        List<CommentModel> userAllComment = userService.findUserAllComment(id);
        return Result.success(userAllComment);
    }
}
