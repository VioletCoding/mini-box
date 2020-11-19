package com.ghw.minibox.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Violet
 * @description 调用PRODUCER
 * @date 2020/11/19
 */
@FeignClient("MINIBOX-PRODUCER")
public interface ProducerFeign {

    @GetMapping("/user/selectOne/{uid}")
    Object selectOne(@PathVariable("uid") Long id);
}
