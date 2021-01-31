package com.ghw.minibox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghw.minibox.mapper.MbpBlockMapper;
import com.ghw.minibox.model.BlockModel;
import com.ghw.minibox.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 版块 业务逻辑 层
 * @date 2021/2/1
 */
@Service
public class MbpBlockServiceImpl implements BaseService<BlockModel> {
    @Resource
    private MbpBlockMapper mbpBlockMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean save(BlockModel model) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean modify(BlockModel model) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public BlockModel findOneById(Long id) {
        return null;
    }

    @Override
    public BlockModel findOne(String column, Object value) {
        return null;
    }

    @Override
    public List<BlockModel> findByModel(BlockModel model) {
        List<BlockModel> blockModelList;
        if (model == null) {
            blockModelList = mbpBlockMapper.selectList(null);
        } else {
            QueryWrapper<BlockModel> queryWrapper = new QueryWrapper<>(model);
            blockModelList = mbpBlockMapper.selectList(queryWrapper);
        }
        return blockModelList;
    }
}
