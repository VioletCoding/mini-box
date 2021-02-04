package com.ghw.minibox.controller;

import com.ghw.minibox.model.PostModel;
import com.ghw.minibox.service.impl.MbpPostServiceImpl;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    /**
     * 帖子列表，条件查询
     *
     * @param postModel 实体
     */
    @PostMapping("list")
    public ResultVo postList(@RequestBody(required = false) PostModel postModel) {
        List<PostModel> postModelList = postService.findByModel(postModel);
        return Result.success(postModelList);
    }

    /**
     * 帖子详情
     *
     * @param id 帖子id
     */
    @GetMapping("detail")
    public ResultVo detail(@RequestParam Long id) {
        Map<String, Object> postDetail = postService.postDetail(id);
        return Result.success(postDetail);
    }

    /**
     * 发表帖子
     *
     * @param postModel 实体
     * @param request   为了拿token
     */
    @PostMapping("add")
    public ResultVo postAdd(@RequestBody @Validated PostModel postModel, HttpServletRequest request) throws Exception {
        boolean beforeSave = postService.beforeSave(postModel, request.getHeader("accessToken"));
        return Result.successFlag(beforeSave);
    }

}
