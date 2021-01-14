package com.ghw.minibox.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Violet
 * @description 是否开启网关鉴权配置类，同时也是Nacos配置的动态刷新类
 * @date 2020/12/23
 */

@RefreshScope
@ToString
@Component
public class EnableAuth {

    /*
     * @author：Violet
     * @description：是否开启网关鉴权，在Nacos配置中心配置
     * @params:
     * @return
     * @date：2021/1/14 14:03
     */
    @Getter
    @Value("${enableAuth.switch}")
    private boolean enableAuth;

    /*
     * @author：Violet
     * @description：放行的url，这里是list集合，需要分隔符才能正常读取yml文件，在Nacos配置中心配置
     * @params:
     * @return
     * @date：2021/1/14 14:03
     */
    @Getter
    @Value("#{'${ignore.url}'.split(',')}")
    private List<String> ignoreUrl;

}
