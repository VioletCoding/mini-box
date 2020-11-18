package com.ghw.minibox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Violet
 * @description 网关主启动类
 * @date 2020/11/18
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayMain {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMain.class, args);
    }
}
