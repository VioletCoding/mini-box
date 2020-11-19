package com.ghw.minibox.nosql.elasticsearch.service;

import com.ghw.minibox.document.ESUser;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Violet
 * @description 用户搜索Service
 * @date 2020/11/19
 */

public interface ESUserService {
    /**
     * 从数据库中导入所有商品到ES
     */
    int importAll(Long uid);

    /**
     * 根据id删除商品
     */
    void delete(Long uid);

    /**
     * 根据id创建商品
     */
    ESUser create(Long uid);

    /**
     * 批量删除商品
     */
    void delete(List<Long> ids);

    /**
     * 根据昵称搜索
     */
    Page<ESUser> search(String keyword, Integer pageNum, Integer pageSize);

}
