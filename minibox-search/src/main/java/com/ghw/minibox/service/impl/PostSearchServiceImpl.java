package com.ghw.minibox.service.impl;

import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbPost;
import com.ghw.minibox.feign.SearchFeignClient;
import com.ghw.minibox.repository.PostSearchRepository;
import com.ghw.minibox.service.PostSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@Service
public class PostSearchServiceImpl implements PostSearchService {

    @Resource
    private PostSearchRepository postSearchRepository;
    @Resource
    private SearchFeignClient searchFeignClient;

    @Override
    public Page<MbPost> search(String title, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return postSearchRepository.findByTitle(title, pageable);
    }

    @Override
    public void getData() {
        ReturnDto<Object> dto = searchFeignClient.getDataFromProducer();
        System.out.println(dto.getData().getClass());
    }
}
