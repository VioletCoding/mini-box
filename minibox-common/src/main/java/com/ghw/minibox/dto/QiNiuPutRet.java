package com.ghw.minibox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Violet
 * @description 七牛云响应类
 * @date 2020/11/28
 */

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QiNiuPutRet {
    private String key;
    private String hash;
    private String bucket;
    private long fsize;
}
