package com.ghw.minibox.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ghw.minibox.mapper.MbpBlockMapper;
import com.ghw.minibox.model.BlockModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 版块 业务逻辑 层
 * @date 2021/2/1
 */
@Service
public class MbpBlockServiceImpl {
    @Resource
    private MbpBlockMapper mbpBlockMapper;

    /**
     * 条件或全查版块
     *
     * @param model 实体
     * @return 版块列表
     */
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
