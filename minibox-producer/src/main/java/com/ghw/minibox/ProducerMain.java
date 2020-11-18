package com.ghw.minibox;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Violet
 * @description 生产者主启动类
 * @date 2020/11/18
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ghw.minibox.mapper")
public class ProducerMain {
    public static void main(String[] args) {
        SpringApplication.run(ProducerMain.class, args);
    }
}