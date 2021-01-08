package com.ghw.minibox.service;

import org.springframework.data.domain.Page;

/**
 * @author Violet
 * @description 通用服务接口
 * @date 2021/1/8
 */

public interface CommonService<T> {

    Page<T> search(String value, Integer pageNum, Integer pageSize);

    void getData();

}
