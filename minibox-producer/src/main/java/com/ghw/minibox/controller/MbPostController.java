package com.ghw.minibox.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.service.MbPostService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.ResultCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * (MbPost)表控制层
 *
 * @author Violet
 * @since 2020-11-19 12:20:17
 */
@RestController
@RequestMapping("post")
@Slf4j
public class MbPostController {
    /**
     * 服务对象
     */
    @Resource
    private MbPostService mbPostService;
    @Resource
    private GenerateResult<String> gr;

    @ApiOperation("发布帖子")
    @AOPLog("发布帖子")
    @PostMapping("publish")
    public ReturnDto<String> publishPost(@RequestBody MbPost mbPost) {
        ReturnDto<ResultCode> returnDto = mbPostService.publish(mbPost);
        if (returnDto.getCode() == ResultCode.OK.getCode())
            return gr.success();
        if (returnDto.getCode() == ResultCode.NOT_FOUND.getCode())
            return gr.custom(ResultCode.NOT_FOUND.getCode(), "该用户不存在");
        return gr.fail();
    }

    @ApiOperation("帖子图片上传")
    @AOPLog("帖子图片上传")
    @PostMapping("upload")
    public ReturnDto<String> addPictureInPost(@RequestParam(value = "multipartFiles") MultipartFile[] multipartFiles,
                                              @RequestParam(value = "tid") Long tid) throws IOException {
        boolean result = mbPostService.addPictureInPost(multipartFiles, tid);
        if (result) return gr.success();
        return gr.fail();
    }

}