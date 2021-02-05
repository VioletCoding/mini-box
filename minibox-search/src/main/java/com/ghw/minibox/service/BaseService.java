package com.ghw.minibox.service;

import org.springframework.data.domain.Page;

/**
 * @author Violet
 * @description 基类 通用接口
 * @date 2021/2/4
 */

public interface BaseService<T> {
    Page<T> search(String value, Integer pageNum, Integer pageSize);
}
