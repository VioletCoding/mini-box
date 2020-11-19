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
    @Value("${config.info}")
    private String configInfo;


    @Override
    public String toString() {
        return "ControlMessageOutput{" +
                "springApplicationName='" + springApplicationName + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", nacosAddress='" + nacosAddress + '\'' +
                ", configInfo='" + configInfo + '\'' +
                '}';
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("MINIBOX-SEARCH 启动完毕 配置信息如下：{}", toString());
    }
}
