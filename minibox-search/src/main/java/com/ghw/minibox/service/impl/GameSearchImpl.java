package com.ghw.minibox.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.entity.MbGame;
import com.ghw.minibox.feign.SearchFeignClient;
import com.ghw.minibox.repository.GameSearchRepository;
import com.ghw.minibox.service.CommonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 游戏搜索
 * @date 2021/1/8
 */
@Service
public class GameSearchImpl implements CommonService<MbGame> {
    @Resource
    private GameSearchRepository gameSearchRepository;
    @Resource
    private SearchFeignClient searchFeignClient;

    @Override
    public Page<MbGame> search(String value, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameSearchRepository.findByName(value, pageable);
    }

    @Override
    public void getData() {
        ReturnDto<Object> dto = searchFeignClient.getDataFromServiceGame();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<MbGame> games = objectMapper.convertValue(dto.getData(), new TypeReference<List<MbGame>>() {
            });
            gameSearchRepository.saveAll(games);
        } catch (Exception e) {
            throw new RuntimeException("获取游戏列表出错 " + e.getMessage());
        }
    }
}
