package com.ghw.minibox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.time.Duration;

/**
 * @author Violet
 * @description 网关 跨域处理器
 * @date 2020/12/24
 */
@Configuration
public class CorsConfig {

    @Bean
    /*
     * @author：Violet
     * @description：该方法用于全局处理跨域请求
     * @params:
     * @return org.springframework.web.cors.reactive.CorsWebFilter
     * @date：2021/1/14 14:01
     */
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.setMaxAge(Duration.ofSeconds(10));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
