package com.ghw.minibox.feign;

import com.ghw.minibox.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Violet
 * @description 调用SERVICE服务获取MySQL数据
 * @date 2021/1/4
 */
@FeignClient(url = "127.0.0.1:20002", name = "service")
public interface SearchFeignClient {

    @PostMapping("/postApi/list")
    ResultVo getPostData();

    @PostMapping("/gameApi/list")
    ResultVo getGameData();
}
