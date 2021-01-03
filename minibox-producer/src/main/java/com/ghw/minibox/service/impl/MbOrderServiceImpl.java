package com.ghw.minibox.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.entity.MbGame;
import com.ghw.minibox.entity.MbOrder;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.mapper.MbGameMapper;
import com.ghw.minibox.mapper.MbOrderMapper;
import com.ghw.minibox.mapper.MbUserMapper;
import com.ghw.minibox.service.MbOrderService;
import com.ghw.minibox.utils.MyException;
import com.ghw.minibox.utils.OrderUtil;
import com.ghw.minibox.utils.ResultCode;
import com.qiniu.util.StringUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Violet
 * @description
 * @date 2020/12/31
 */
@Service
public class MbOrderServiceImpl implements MbOrderService {
    @Resource
    private MbOrderMapper orderMapper;
    @Resource
    private MbGameMapper gameMapper;
    @Resource
    private MbUserMapper userMapper;
    @Resource
    private OrderUtil orderUtil;
    @Resource
    private RedisUtil redis;

    /**
     * 生成订单
     *
     * @param mbOrder 实例
     * @return 是否成功
     */
    @Override
    public MbOrder generateOrder(MbOrder mbOrder) throws JsonProcessingException {
        String v = redis.get(RedisUtil.ORDER_PREFIX + mbOrder.getUid() + mbOrder.getOrderId());
        if (!StringUtils.isNullOrEmpty(v)) {
            throw new MyException("订单已创建");
        }

        Long orderId = orderUtil.getOrderId();
        mbOrder.setOrderId(orderId);

        MbGame mbGame = gameMapper.queryById(mbOrder.getOrderGameId());
        if (mbGame == null) {
            throw new MyException("未找到该游戏");
        }

        MbUser mbUser = userMapper.queryById(mbOrder.getUid());
        if (mbUser == null) {
            throw new MyException("未找到该用户");
        }

        mbGame.setCommentList(null)
                .setMbBlock(null)
                .setTagList(null)
                .setMbComment(null)
                .setPhotoList(null);

        mbOrder.setMbGame(mbGame);
        ObjectMapper om = new ObjectMapper();
        redis.set(RedisUtil.ORDER_PREFIX + mbOrder.getUid() + orderId, om.writeValueAsString(mbOrder), 300L);
        mbOrder.setSuccess(orderUtil.NOT_SUCCESS);
        orderMapper.insert(mbOrder);
        MbOrder notPayOrder = orderMapper.queryById(mbOrder.getId());

        return notPayOrder.setMbGame(mbGame);
    }

    /**
     * 下订单成功后存储
     *
     * @param mbOrder 实例
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrder(MbOrder mbOrder) {

        if (mbOrder.getOrderId() == null) {
            throw new MyException("orderId为空");
        }

        String value = redis.get(RedisUtil.ORDER_PREFIX + mbOrder.getUid() + mbOrder.getOrderId());

        if (StringUtils.isNullOrEmpty(value)) {
            throw new MyException(ResultCode.ORDER_CANCEL.getMessage());
        }
        mbOrder.setSuccess(orderUtil.SUCCESS);
        int update = orderMapper.update(mbOrder);
        if (update > 0) {
            redis.remove(RedisUtil.ORDER_PREFIX + mbOrder.getUid() + mbOrder.getOrderId());
            return true;
        }

        return false;
    }

    /**
     * 取消订单
     *
     * @param mbOrder 实例
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelOrder(MbOrder mbOrder) {
        redis.remove(RedisUtil.ORDER_PREFIX + mbOrder.getUid() + mbOrder.getOrderId());
        return orderMapper.delete(mbOrder) > 1;
    }
}
