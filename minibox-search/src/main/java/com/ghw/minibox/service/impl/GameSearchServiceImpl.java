package com.ghw.minibox.service.impl;

import com.ghw.minibox.es.ESGameModel;
import com.ghw.minibox.repository.GameSearchRepository;
import com.ghw.minibox.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description 游戏搜索
 * @date 2021/1/8
 */
@Service
public class GameSearchServiceImpl implements BaseService<ESGameModel> {
    @Resource
    private GameSearchRepository gameSearchRepository;

    @Override
    public Page<ESGameModel> search(String value, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameSearchRepository.findByName(value, pageable);
    }

}
