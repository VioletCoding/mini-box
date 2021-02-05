package com.ghw.minibox.controller;

import cn.hutool.core.util.IdUtil;
import com.ghw.minibox.component.QiNiuUtil;
import com.ghw.minibox.service.impl.MbpEchartsServiceImpl;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Violet
 * @description 公用控制器，一些可以公用的Api
 * @date 2021/1/28
 */
@RestController
@RequestMapping("publicApi")
public class PublicController {
    @Resource
    private QiNiuUtil qiNiuUtil;
    @Resource
    private MbpEchartsServiceImpl echartsService;

    @ApiOperation("上传文件公用接口,可多文件上传,单次单个文件最大3MB,单次全部文件加起来不能超过30MB")
    @PostMapping("upload")
    public ResultVo upload(@RequestParam("multipartFile") MultipartFile[] multipartFile) throws IOException {
        if (multipartFile.length < 1) {
            return Result.fail("文件列表为空");
        }
        Map<String, Object> map = new HashMap<>();
        List<String> linkList = new ArrayList<>();
        for (MultipartFile m : multipartFile) {
            String originalFilename = m.getOriginalFilename();
            String simpleUUID = IdUtil.fastSimpleUUID();
            String fileLink = qiNiuUtil.syncUpload(simpleUUID + originalFilename, m.getBytes());
            linkList.add(fileLink);
        }
        map.put("images", linkList);
        return Result.success(map);
    }

    @ApiOperation("echarts数据接口")
    @GetMapping("echarts")
    public ResultVo echartsData() {
        Map<String, Object> build = echartsService.build();
        return Result.success(build);
    }
}
