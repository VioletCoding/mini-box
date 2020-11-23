package com.ghw.minibox.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.dto.ReturnDto;
import org.springframework.stereotype.Component;

/**
 * @author Violet
 * @description 将对象注入IOC
 * @date 2020/11/18
 */
@Component
public class GenerateBean {
    public ReturnDto<Object> getReturnDto() {
        return new ReturnDto<>();
    }

    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
