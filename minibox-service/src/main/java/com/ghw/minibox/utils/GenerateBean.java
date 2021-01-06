package com.ghw.minibox.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * @author Violet
 * @description
 * @date 2021/1/6
 */
@Component
public class GenerateBean {

    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
