package com.ghw.minibox.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Violet
 * @description 将对象注入IOC
 * @date 2020/11/18
 */
@Component
public class GenerateBean {
    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
