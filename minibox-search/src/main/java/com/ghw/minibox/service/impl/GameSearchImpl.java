package com.ghw.minibox.service.impl;

import com.ghw.minibox.es.ESMbGame;
import com.ghw.minibox.repository.GameSearchRepository;
import com.ghw.minibox.service.CommonService;
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
public class GameSearchImpl implements CommonService<ESMbGame> {
    @Resource
    private GameSearchRepository gameSearchRepository;

    @Override
    public Page<ESMbGame> search(String value, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameSearchRepository.findByName(value, pageable);
    }

}
