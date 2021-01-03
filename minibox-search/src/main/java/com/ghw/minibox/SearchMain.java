package com.ghw.minibox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Violet
 * @description 搜索引擎主启动类
 * @date 2020/11/19
 */
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients
public class SearchMain {
    public static void main(String[] args) {
        SpringApplication.run(SearchMain.class, args);
    }
}
