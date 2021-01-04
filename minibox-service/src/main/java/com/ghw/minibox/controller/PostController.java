package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.service.impl.PostImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@RequestMapping("postService")
@RestController
public class PostController {
    @Resource
    private PostImpl post;
    @Resource
    private GenerateResult<Object> gr;

    /**
     * 获取帖子列表
     *
     * @param mbPost 实例
     * @return 帖子列表
     */
    @GetMapping("all")
    public ReturnDto<Object> showPostList(MbPost mbPost) {
        return gr.success(post.selectAll(mbPost));
    }

    /**
     * 发表帖子
     *
     * @param mbPost 实例
     * @return 是否成功
     */
    @PostMapping("publish")
    public ReturnDto<Object> publishPost(@RequestBody @Validated MbPost mbPost) {
        boolean insert = post.insert(mbPost);
        if (insert) {
            return gr.success();
        }
        return gr.fail();
    }

    /**
     * 展示帖子详情
     *
     * @param id 帖子id
     * @return 帖子详情
     */
    @GetMapping("detail")
    public ReturnDto<Object> showPostDetail(@RequestParam Long id) {
        MbPost mbPost = post.selectOne(id);
        return gr.success(mbPost);
    }


}
