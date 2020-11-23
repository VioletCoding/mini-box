package com.ghw.minibox;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Violet
 * @description 生产者主启动类
 * @date 2020/11/18
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
@EnableCaching
@MapperScan("com.ghw.minibox.mapper")
public class ProducerMain {
    public static void main(String[] args) {
        SpringApplication.run(ProducerMain.class, args);
    }
}
