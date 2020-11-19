package com.ghw.minibox.nosql.elasticsearch.controller;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.document.ESUser;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.nosql.elasticsearch.service.ESUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description 用户搜索Controller
 * @date 2020/11/19
 */
@RestController
@Slf4j
@RequestMapping("search")
public class ESUserController {
    @Resource
    private ESUserService esUserService;

    @Resource
    private GenerateResult<ESUser> gs;

    @GetMapping("importAll/{uid}")
    public ReturnDto<ESUser> importAll(@PathVariable("uid") Long uid) {
        esUserService.importAll(uid);
        return gs.success();
    }

    @PostMapping("create/{uid}")
    public ReturnDto<ESUser> create(@PathVariable("uid") Long uid) {
        ESUser esUser = esUserService.create(uid);
        if (esUser != null)
            return gs.success(esUser);
        return gs.fail();

    }
}
