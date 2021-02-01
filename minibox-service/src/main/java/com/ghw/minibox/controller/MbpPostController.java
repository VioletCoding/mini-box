package com.ghw.minibox.controller;

import com.ghw.minibox.model.PostModel;
import com.ghw.minibox.service.impl.MbpPostServiceImpl;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Violet
 * @description 帖子 控制层
 * @date 2021/2/1
 */
@RestController
@RequestMapping("postApi")
public class MbpPostController {
    @Resource
    private MbpPostServiceImpl postService;

    @PostMapping("list")
    public ResultVo postList(@RequestBody(required = false) PostModel postModel){
        List<PostModel> postModelList = postService.findByModel(postModel);
        return Result.success(postModelList);
    }

    @PostMapping("add")
    public ResultVo postAdd(@RequestBody @Validated PostModel postModel, HttpServletRequest request) throws Exception {
        boolean beforeSave = postService.beforeSave(postModel, request.getHeader("accessToken"));
        return Result.successFlag(beforeSave);
    }

}
