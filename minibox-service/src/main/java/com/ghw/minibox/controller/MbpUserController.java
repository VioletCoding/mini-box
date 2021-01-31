package com.ghw.minibox.controller;

import com.ghw.minibox.model.UserModel;
import com.ghw.minibox.service.impl.MbpUserServiceImpl;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
        UserModel userModel = userService.findOne("id", id);
        return Result.success(userModel);
    }
}
