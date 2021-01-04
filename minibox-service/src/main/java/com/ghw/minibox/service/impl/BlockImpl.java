package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbBlock;
import com.ghw.minibox.mapper.MbBlockMapper;
import com.ghw.minibox.service.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2021/1/4
 */
@Service
public class BlockImpl implements CommonService<MbBlock> {
    @Resource
    private MbBlockMapper blockMapper;

    @Override
    public List<MbBlock> selectAll(MbBlock param) {
        return blockMapper.queryAll(null);
    }

    @Override
    public MbBlock selectOne(Long id) {
        return null;
    }

    @Override
    public boolean insert(MbBlock entity) {
        return false;
    }

    @Override
    public boolean update(MbBlock entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
