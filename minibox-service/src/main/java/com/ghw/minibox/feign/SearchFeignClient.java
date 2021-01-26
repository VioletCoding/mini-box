package com.ghw.minibox.feign;

import com.ghw.minibox.dto.ReturnDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Violet
 * @description 调用搜索服务
 * @date 2021/1/9
 */
@FeignClient(url = "localhost:20003", name = "search")
public interface SearchFeignClient {

    /**
     * 通知Search服务更新Elasticsearch数据
     *
     */
    @GetMapping("/search/refresh")
    ReturnDto refreshData();
}
