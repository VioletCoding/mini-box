package com.ghw.minibox.feign;

import com.ghw.minibox.vo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Violet
 * @description 调用SERVICE服务获取MySQL数据
 * @date 2021/1/4
 */
@FeignClient(url = "127.0.0.1:20002", name = "service")
public interface SearchFeignClient {

    /**
     * 获取全部帖子列表
     *
     * @return 帖子列表
     */
    @GetMapping("/post/all")
    ResultVo getDataFromService();

    /**
     * 获取全部游戏列表
     *
     * @return 游戏列表
     */
    @GetMapping("/game/all")
    ResultVo getDataFromServiceGame();


}
