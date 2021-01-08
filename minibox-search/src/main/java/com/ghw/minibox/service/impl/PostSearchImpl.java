package com.ghw.minibox.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.feign.SearchFeignClient;
import com.ghw.minibox.repository.PostSearchRepository;
import com.ghw.minibox.service.CommonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 帖子ES搜索实现
 * @date 2021/1/4
 */
@Service
public class PostSearchImpl implements CommonService<MbPost> {
    @Resource
    private PostSearchRepository postSearchRepository;
    @Resource
    private SearchFeignClient searchFeignClient;

    /**
     * 简单搜索
     *
     * @param title    帖子标题
     * @param pageNum  第几页
     * @param pageSize 每页多少条
     * @return 分页后的帖子数
     */
    @Override
    public Page<MbPost> search(String title, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return postSearchRepository.findByTitleOrContent(title, title, pageable);
    }

    /**
     * 从Service模块获取MySQL数据
     */
    @Override
    public void getData() {
        ReturnDto<Object> dto = searchFeignClient.getDataFromService();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<MbPost> posts = objectMapper.convertValue(dto.getData(), new TypeReference<List<MbPost>>() {
            });
            postSearchRepository.saveAll(posts);
        } catch (Exception e) {
            throw new RuntimeException("从Service服务获取到的数据 转换失败" + e.getMessage());
        }

    }
}
