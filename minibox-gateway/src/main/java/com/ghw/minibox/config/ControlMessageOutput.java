package com.ghw.minibox.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author Violet
 * @description 实现ApplicationRunner接口，在程序启动完成后执行代码
 * RefreshScope注解，实现Nacos动态配置文件刷新
 * Component注解，将本类加入IOC容器
 * Slf4j注解，开启日志功能
 * Value注解，读取配置文件中对应层级关系的配置信息
 * @date 2020/11/9
 */
@Component
@RefreshScope
@Slf4j
public class ControlMessageOutput implements ApplicationRunner {
    @Value("${spring.application.name}")
    private String springApplicationName;
    @Value("${server.port}")
    private String serverPort;
    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String nacosAddress;
    @Value("${management.endpoint.web.exposure.include}")
    private String managementEndpoint;
    @Value("${config.info}")
    private String configInfo;


    /**
     * 打印启动信息
     * @param args 可以从main函数中取得该参数，在java -jar的时候可以传入参数
     */
    @Override
    public void run(ApplicationArguments args) {
        log.info("Spring Cloud Gateway 正在启动...");
        log.info("服务名为:{}", springApplicationName);
        log.info("端口号为:{}", serverPort);

        log.info("正在连接到Nacos注册中心...");
        log.info("Nacos地址为:{}", nacosAddress);

        log.info("正在配置网关监控端点...");
        log.info("网关监控端点为:{}", managementEndpoint);

        log.info("检测是否正确获取配置中心的配置...{}", configInfo);

        log.info("迷你盒网关启动成功!!!");

    }
}
