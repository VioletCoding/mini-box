package com.ghw.minibox.config;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2020/12/23
 */

@RefreshScope
@ToString
@Slf4j
@Component
public class EnableAuth implements ApplicationRunner {

    //是否开启网关鉴权
    @Getter
    @Value("${enableAuth.switch}")
    private final boolean enableAuth = true;

    /**
     * 放行的url
     */
    @Getter
    @Value("#{'${ignore.url}'.split(',')}")
    private List<String> ignoreUrl;


    /**
     * 打印启动信息
     *
     * @param args 可以从main函数中取得该参数，在java -jar的时候可以传入参数
     */
    @Override
    public void run(ApplicationArguments args) {
        log.info("SpringCloud Gateway启动完成，配置信息为：{}", toString());
    }
}
