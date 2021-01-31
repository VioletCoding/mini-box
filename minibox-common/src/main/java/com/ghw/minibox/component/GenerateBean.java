package com.ghw.minibox.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * @author Violet
 * @description 生成一些常用的类，注入到IOC
 * @date 2021/1/6
 */
@Component
public class GenerateBean {

    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
