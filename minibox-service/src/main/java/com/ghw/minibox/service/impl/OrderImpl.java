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
import com.ghw.minibox.service.CommonService;
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
    private MbUserMapper userMapper;
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
    private boolean buyFlag(Long userId, Long gameId, Integer successFlag) {
        MbOrder mbOrder = new MbOrder();
        mbOrder.setUid(userId).setOrderGameId(gameId).setSuccess(successFlag);
        List<MbOrder> orders = orderMapper.queryAll(mbOrder);
        //如果有记录，那就是买过了
        return orders.size() != 0;
    }


    /**
     * 生成订单
     *
     * @param mbOrder 实例
     * @return 是否成功
     */
    @Override
    public Object insert(MbOrder mbOrder) throws JsonProcessingException {
        //检测下是否已经购买过了
        if (this.buyFlag(mbOrder.getUid(), mbOrder.getOrderGameId(), orderUtil.SUCCESS)) {
            log.error("id为 {} 的用户已经购买过 id为 {} 的游戏", mbOrder.getUid(), mbOrder.getOrderGameId());
            throw new RuntimeException("您已购买过该游戏");
        }

        String v = redisUtil.get(RedisUtil.ORDER_PREFIX + mbOrder.getUid() + mbOrder.getOrderId());

        if (!StringUtils.isNullOrEmpty(v)) throw new RuntimeException("订单已创建");

        Long orderId = orderUtil.getOrderId();
        mbOrder.setOrderId(orderId);

        MbGame mbGame = gameMapper.queryById(mbOrder.getOrderGameId());
        if (mbGame == null) throw new RuntimeException("未找到该游戏");


        MbUser mbUser = userMapper.queryById(mbOrder.getUid());
        if (mbUser == null) throw new RuntimeException("未找到该用户");


        mbGame.setCommentList(null).setMbBlock(null).setTagList(null).setPhotoList(null);

        mbOrder.setMbGame(mbGame);
        ObjectMapper om = generateBean.getObjectMapper();
        //生成5分钟有效时间的订单
        redisUtil.set(RedisUtil.ORDER_PREFIX + mbOrder.getUid() + orderId, om.writeValueAsString(mbOrder), 300L);
        //但是此时订单未完成
        mbOrder.setSuccess(orderUtil.NOT_SUCCESS);
        orderMapper.insert(mbOrder);
        MbOrder notPayOrder = orderMapper.queryById(mbOrder.getId());
        return notPayOrder.setMbGame(mbGame);
    }

    /**
     * 下订单成功后存储
     *
     * @param entity 实例
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(MbOrder entity) {

        if (entity.getOrderId() == null) throw new RuntimeException("orderId为空");

        String value = redisUtil.get(RedisUtil.ORDER_PREFIX + entity.getUid() + entity.getOrderId());

        if (StringUtils.isNullOrEmpty(value)) throw new RuntimeException(ResultCode.ORDER_CANCEL.getMessage());

        entity.setSuccess(orderUtil.SUCCESS);
        //更新订单信息
        int update = orderMapper.update(entity);
        if (update > 0) {
            redisUtil.remove(RedisUtil.ORDER_PREFIX + entity.getUid() + entity.getOrderId());
            redisUtil.remove(RedisUtil.REDIS_PREFIX + RedisUtil.USER_PREFIX + entity.getUid());
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
    @Transactional(rollbackFor = Throwable.class)
    public boolean cancelOrder(MbOrder mbOrder) {
        redisUtil.remove(RedisUtil.ORDER_PREFIX + mbOrder.getUid() + mbOrder.getOrderId());
        return orderMapper.deleteById(mbOrder) > 1;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
