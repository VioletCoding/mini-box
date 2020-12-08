package com.ghw.minibox.service.impl;

import com.ghw.minibox.entity.MbBlock;
import com.ghw.minibox.mapper.MbBlockMapper;
import com.ghw.minibox.service.MbBlockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MbBlock)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 12:20:09
 */
@Service
public class MbBlockServiceImpl implements MbBlockService {
    @Resource
    private MbBlockMapper mbBlockMapper;


    /**
     * 首页展示所有版块
     *
     * @return 所有版块的名称，仅仅只是名称和bid（版块ID）
     */
    @Override
    public List<MbBlock> showAllBlock() {
        return mbBlockMapper.queryAll();
    }
}