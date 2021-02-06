package com.ghw.minibox.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.GenerateBean;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.MbpGameMapper;
import com.ghw.minibox.mapper.MbpOrderMapper;
import com.ghw.minibox.model.GameModel;
import com.ghw.minibox.model.OrderModel;
import com.ghw.minibox.utils.OrderUtil;
import com.ghw.minibox.utils.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author Violet
 * @description 订单 业务逻辑
 * @date 2021/2/2
 */
@Service
public class MbpOrderServiceImpl {
    @Resource
    private MbpOrderMapper mbpOrderMapper;
    @Resource
    private MbpGameMapper mbpGameMapper;
    @Resource
    private OrderUtil orderUtil;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private GenerateBean generateBean;

    /**
     * 检查是否买过此游戏
     *
     * @param userId      用户id
     * @param gameId      游戏id
     * @param successFlag 是否成功
     */
    public boolean buyFlag(Long userId, Long gameId, String successFlag) {
        OrderModel mbOrder = new OrderModel();
        mbOrder.setUserId(userId).setGameId(gameId).setSuccessFlag(successFlag);
        QueryWrapper<OrderModel> wrapper = new QueryWrapper<>();
        wrapper.select("user_id", "game_id")
                .eq("user_id", userId)
                .eq("game_id", gameId);
        List<OrderModel> models = mbpOrderMapper.selectList(wrapper);
        return models.size() > 0;
    }

    /**
     * 创建订单
     *
     * @param orderModel 实体
     */
    public Object create(OrderModel orderModel) throws JsonProcessingException {
        //检测下是否已经购买过了
        if (this.buyFlag(orderModel.getUserId(), orderModel.getGameId(), orderUtil.SUCCESS)) {
            throw new MiniBoxException(ResultCode.ORDER_PAYED.getMessage());
        }
        //是否创建了订单，超时时间5分钟，如果创建了就直接return订单信息
        String value = redisUtil.get(RedisUtil.ORDER_PREFIX + orderModel.getUserId() + orderModel.getGameId());
        if (!StrUtil.isBlank(value)) {
            return value;
        }
        //检验一下游戏是否可购买
        QueryWrapper<GameModel> gameModelQueryWrapper = new QueryWrapper<>();
        gameModelQueryWrapper.eq("id", orderModel.getGameId());
        GameModel gameModel = mbpGameMapper.selectOne(gameModelQueryWrapper);
        if (gameModel == null) {
            throw new MiniBoxException(ResultCode.GAME_CANT_BE_BUY.getMessage());
        }
        //生成订单号
        Long orderId = orderUtil.getOrderId();
        orderModel.setOrderId(orderId);
        //设置游戏信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("gameInfo", gameModel);
        map.put("orderInfo", orderModel);
        ObjectMapper om = generateBean.getObjectMapper();
        //生成5分钟有效时间的订单
        //Redis key格式： order:用户id:游戏id:orderId
        String key = RedisUtil.ORDER_PREFIX + orderModel.getUserId() + ":" + orderModel.getGameId();
        //但是此时订单未完成
        orderModel.setSuccessFlag(orderUtil.NOT_SUCCESS);
        String orderJson = om.writeValueAsString(orderModel);
        redisUtil.set(key, orderJson, 300L);
        return map;
    }

    /**
     * 提交订单
     *
     * @param orderModel 实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public boolean submit(OrderModel orderModel) {
        //Redis key格式： order:用户id:游戏id:orderId
        String key = RedisUtil.ORDER_PREFIX + orderModel.getUserId() + ":" + orderModel.getGameId();
        String value = redisUtil.get(key);
        if (StrUtil.isBlank(value)) {
            return false;
        }
        orderModel.setSuccessFlag(orderUtil.SUCCESS);
        //插入订单信息
        int insert = mbpOrderMapper.insert(orderModel);
        if (insert > 0) {
            //删除缓存
            redisUtil.remove(key);
            //个人信息缓存
            redisUtil.remove(RedisUtil.REDIS_PREFIX + RedisUtil.USER_PREFIX + orderModel.getUserId());
            return true;
        }
        return false;
    }

    /**
     * 取消订单
     *
     * @param orderModel 实体
     */
    public boolean cancelOrder(OrderModel orderModel) {
        String key = RedisUtil.ORDER_PREFIX + orderModel.getUserId() + ":" + orderModel.getGameId();
        return redisUtil.remove(key);
    }

}
