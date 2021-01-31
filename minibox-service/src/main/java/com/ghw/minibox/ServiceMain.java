package com.ghw.minibox;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Violet
 * @description 启动类
 * @date 2021/1/4
 */
@MapperScan("com.ghw.minibox.mapper")
@SpringBootApplication
@EnableAsync
//@EnableDiscoveryClient
@EnableFeignClients
public class ServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(ServiceMain.class, args);
    }
}
