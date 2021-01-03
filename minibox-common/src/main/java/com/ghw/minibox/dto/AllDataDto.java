package com.ghw.minibox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Violet
 * @description 任意数据组装DTO
 * @date 2021/1/4
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllDataDto {

    private Long id;

    private String message;

    private Object data;

}
