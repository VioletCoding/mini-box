package com.ghw.minibox.service.impl;

import com.ghw.minibox.es.ESPostModel;
import com.ghw.minibox.repository.PostSearchRepository;
import com.ghw.minibox.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description 帖子ES搜索实现
 * @date 2021/1/4
 */
@Service
@Slf4j
public class PostSearchServiceImpl implements BaseService<ESPostModel> {
    @Resource
    private PostSearchRepository postSearchRepository;

    /**
     * 简单搜索
     *
     * @param title    帖子标题
     * @param pageNum  第几页
     * @param pageSize 每页多少条
     * @return 分页后的帖子数
     */
    @Override
    public Page<ESPostModel> search(String title, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return postSearchRepository.findByTitleOrContent(title, title, pageable);
    }

}
