package com.ghw.minibox.feign;

import com.ghw.minibox.dto.ReturnDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@FeignClient(url = "localhost:20002",name = "producer")
public interface SearchFeignClient {

    @GetMapping("/post/showAll")
    ReturnDto<Object> getDataFromProducer();

}
