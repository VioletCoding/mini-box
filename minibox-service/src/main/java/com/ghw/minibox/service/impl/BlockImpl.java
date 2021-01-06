package com.ghw.minibox.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.entity.MbBlock;
import com.ghw.minibox.mapper.MbBlockMapper;
import com.ghw.minibox.service.CommonService;
import com.ghw.minibox.utils.GenerateBean;
import com.qiniu.util.StringUtils;
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
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private GenerateBean generateBean;

    @Override
    public List<MbBlock> selectAll(MbBlock param) throws JsonProcessingException {
        //先查缓存
        ObjectMapper objectMapper = generateBean.getObjectMapper();
        String fromRedis = redisUtil.get(RedisUtil.REDIS_PREFIX + RedisUtil.BLOCK_PREFIX);
        if (!StringUtils.isNullOrEmpty(fromRedis)) {
            return objectMapper.readValue(fromRedis, new TypeReference<List<MbBlock>>() {
            });
        }

        List<MbBlock> blocks = blockMapper.queryAll(null);
        //打进缓存
        redisUtil.set(RedisUtil.REDIS_PREFIX + RedisUtil.BLOCK_PREFIX, objectMapper.writeValueAsString(blocks), 86400L);
        return blocks;
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
