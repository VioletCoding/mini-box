package com.ghw.minibox.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
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
@Component
public class EnableAuth {

    //是否开启网关鉴权
    @Getter
    @Value("${enableAuth.switch}")
    private boolean enableAuth;

    /**
     * 放行的url
     */
    @Getter
    @Value("#{'${ignore.url}'.split(',')}")
    private List<String> ignoreUrl;

}
