package com.ghw.minibox.component;

import com.ghw.minibox.dto.ReturnDto;
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
    public ReturnDto<Object> getReturnDto() {
        return new ReturnDto<>();
    }
}
