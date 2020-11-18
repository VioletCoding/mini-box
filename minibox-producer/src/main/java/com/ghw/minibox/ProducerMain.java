package com.ghw.minibox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author Violet
 * @description 生产者主启动类
 * @date 2020/11/18
 */
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
public class ProducerMain {
    public static void main(String[] args) {
        SpringApplication.run(ProducerMain.class, args);
    }
}
