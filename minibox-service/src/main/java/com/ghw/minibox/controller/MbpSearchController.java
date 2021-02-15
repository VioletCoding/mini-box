package com.ghw.minibox.controller;

import com.ghw.minibox.service.impl.MbpSearchServiceImpl;
import com.ghw.minibox.utils.Result;
import com.ghw.minibox.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Violet
 * @description 搜索控制器
 * @date 2021/2/13
 */
@RestController
@RequestMapping("searchApi")
public class MbpSearchController {
    @Resource
    private MbpSearchServiceImpl mbpSearchService;

    @GetMapping("search")
    public ResultVo search(@RequestParam String keyword) {
        Map<String, Object> search = mbpSearchService.search(keyword);
        return Result.success(search);
    }
}
