package com.ghw.minibox.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.entity.MbGame;
import com.ghw.minibox.entity.MbOrder;
import com.ghw.minibox.mapper.MbGameMapper;
import com.ghw.minibox.mapper.MbOrderMapper;
import com.ghw.minibox.service.CommonService;
import com.ghw.minibox.utils.AOPLog;
import com.ghw.minibox.utils.GenerateBean;
import com.ghw.minibox.utils.OrderUtil;
import com.ghw.minibox.utils.ResultCode;
import com.qiniu.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Violet
 * @description 订单实现类
 * @date 2021/1/9
 */
@Service
@Slf4j
public class OrderImpl implements CommonService<MbOrder> {


    @Resource
    private MbOrderMapper orderMapper;
    @Resource
    private MbGameMapper gameMapper;
    @Resource
    private OrderUtil orderUtil;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private GenerateBean generateBean;


    @Override
    public List<MbOrder> selectAll(MbOrder param) {
        return null;
    }

    @Override
    public MbOrder selectOne(Long id) {
        return null;
    }


    /**
     * 是否已经购买这个游戏了
     *
     * @param gameId      游戏id
     * @param userId      用户id
     * @param successFlag 订单是否成功
     */
    @AOPLog("检查是否购买过此游戏")
    public boolean buyFlag(Long userId, Long gameId, Integer successFlag) {
        MbOrder mbOrder = new MbOrder();
        mbOrder.setUid(userId).setOrderGameId(gameId).setSuccess(successFlag);
        List<MbOrder> orders = orderMapper.queryAll(mbOrder);
        //如果有记录，那就是买过了
        return orders.size() > 0;
    }

    /**
     * 生成订单
     *
     * @param mbOrder 实例
     * @return 是否成功
     */
    @AOPLog("生成订单")
    public Object create(MbOrder mbOrder) throws JsonProcessingException {
        //检测下是否已经购买过了
        if (this.buyFlag(mbOrder.getUid(), mbOrder.getOrderGameId(), orderUtil.SUCCESS))
            return ResultCode.ORDER_PAYED.getMessage();

        //是否创建了订单，超时时间5分钟，如果创建了就直接return订单信息
        String v = redisUtil.get(RedisUtil.ORDER_PREFIX + mbOrder.getUid() + mbOrder.getOrderGameId());
        if (!StringUtils.isNullOrEmpty(v)) return v;

        //检验一下游戏是否可购买
        MbGame mbGame = gameMapper.queryById(mbOrder.getOrderGameId());
        if (mbGame == null) return ResultCode.GAME_CANT_BE_BUY.getMessage();

        //生成订单号
        Long orderId = orderUtil.getOrderId();
        mbOrder.setOrderId(orderId);
        //设置游戏信息
        mbOrder.setMbGame(mbGame);
        ObjectMapper om = generateBean.getObjectMapper();
        //生成5分钟有效时间的订单
        //Redis key格式： order:用户id:游戏id:orderId
        String key = RedisUtil.ORDER_PREFIX + mbOrder.getUid() + ":" + mbOrder.getOrderGameId();
        //但是此时订单未完成
        mbOrder.setSuccess(orderUtil.NOT_SUCCESS);
        String orderJson = om.writeValueAsString(mbOrder);
        redisUtil.set(key, orderJson, 300L);
        return mbOrder;
    }

    /**
     * 下订单成功后存储
     *
     * @param mbOrder 实例
     * @return 是否成功
     */
    @AOPLog("提交订单")
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public boolean insert(MbOrder mbOrder){

        //Redis key格式： order:用户id:游戏id:orderId
        String key = RedisUtil.ORDER_PREFIX + mbOrder.getUid() + ":" + mbOrder.getOrderGameId();
        String value = redisUtil.get(key);

        if (StringUtils.isNullOrEmpty(value))
            return false;

        mbOrder.setSuccess(orderUtil.SUCCESS);
        //插入订单信息
        int insert = orderMapper.insert(mbOrder);
        if (insert > 0) {
            //删除缓存
            redisUtil.remove(key);
            //个人信息缓存
            redisUtil.remove(RedisUtil.REDIS_PREFIX + RedisUtil.USER_PREFIX + mbOrder.getUid());
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
    public boolean cancelOrder(MbOrder mbOrder) {
        String key = RedisUtil.ORDER_PREFIX + mbOrder.getUid() + ":" + mbOrder.getOrderGameId();
        redisUtil.remove(key);
        return true;
    }


    @Override
    public boolean update(MbOrder entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
