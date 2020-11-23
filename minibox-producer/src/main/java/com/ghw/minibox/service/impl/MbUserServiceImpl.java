package com.ghw.minibox.service.impl;


import cn.hutool.crypto.digest.MD5;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.mapper.MbUserMapper;
import com.ghw.minibox.service.MbUserService;
import com.ghw.minibox.utils.DefaultUserInfoEnum;
import com.ghw.minibox.utils.RedisPrefix;
import com.ghw.minibox.utils.ResultCode;
import com.ghw.minibox.utils.SendEmail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * (MbUser)表服务实现类
 *
 * @author Violet
 * @since 2020-11-19 12:20:22
 */
@Service
@Slf4j
public class MbUserServiceImpl implements MbUserService {
    @Resource
    private MbUserMapper mbUserMapper;
    @Resource
    private SendEmail sendEmail;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 查询Redis持久化记录，如果返回的对象不为空，就证明邮箱username已经被占用
     * <p>
     * 从Redis检查是否已经有该用户，看看是否已发送过验证码，如果发送过了，300秒后才能再次请求
     * <p>
     * 如果用户已经持久化，也就是注册成功了，直接返回用户已存在
     *
     * @param username 邮箱
     * @return 实体
     */
    @Override
    public String queryByUsername(String username) {
        Boolean result = redisUtil.exist(RedisPrefix.USER_TEMP.getPrefix() + username);
        if (result) return ResultCode.HAS_BEEN_SENT.getMessage();
        Boolean exist = redisUtil.exist(RedisPrefix.USER_EXIST.getPrefix() + username);
        log.info("从Redis检查该用户是否存在==>{}", exist);
        if (exist) return ResultCode.USER_EXIST.getMessage();
        return ResultCode.BAD_REQUEST.getMessage();
    }


    /**
     * 异步发送邮件，并写入Redis
     * <p>
     * 如果queryByUsername校验通过，那么可以往下执行这个方法
     * <p>
     * 该方法会生成一个6位数的随机验证码
     * <p>
     * 并且放到邮件内容发送给注册的用户
     * <p>
     * 验证码会放进Redis里保存五分钟，300秒后自动失效
     *
     * @param username 邮箱
     */
    @Async
    @Override
    public void sendEmail(String username) throws EmailException {
        String subject = "Minibox验证码，请注意查收！";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int randomNum;
        for (int i = 0; i < 6; i++) {
            randomNum = random.nextInt(10);
            sb.append(randomNum);
        }
        sendEmail.createEmail(username, subject, "尊敬的迷你盒用户，您本次的验证码为：" + sb.toString() + " 本次验证码有效时间为5分钟！");
        log.info("本次邮件发送给==>{},主题为==>{},内容为==>{}", username, subject, sb.toString());
        redisUtil.set(RedisPrefix.USER_TEMP.getPrefix() + username, sb.toString());
        redisUtil.expire(RedisPrefix.USER_TEMP.getPrefix() + username, 300L);
        log.info("本次存入Redis的Key==>{},Value==>{}", RedisPrefix.USER_TEMP.getPrefix() + username, sb.toString());
    }

    /**
     * 校验 验证码
     * <p>
     * 校验传入的验证码是否跟在Redis保存的验证码一致
     *
     * @param key   邮箱
     * @param value 验证码
     * @return true or false
     */
    @Override
    public boolean authRegCode(String key, String value) {
        String valueFromRedis = redisUtil.get(RedisPrefix.USER_TEMP.getPrefix() + key);
        log.info("从Redis获取到的value==>{}", valueFromRedis);
        return value.equals(valueFromRedis);
    }

    /**
     * 校验都成功后可以允许注册
     * <p>
     * 默认用户名生成，生成一个带 "用户_" 前缀的昵称，后缀为随机值，随机值为MongoDB ID生成策略
     * <p>
     * 密码会使用MD5简单加密一次，让存储的密码不是明文
     * <p>
     * 用户数据成功持久化到MySQL后，会从Redis移除刚才生成的300秒有效期的验证码，前缀为RedisPrefix.USER_TEMP.getPrefix()
     * <p>
     * 移除Redis验证码后，会把该用户的部分信息持久化到Redis，以便加快响应后续请求，
     * 特别是同一个邮箱反复点击注册，可以拦截大量请求，前缀是RedisPrefix.USER_EXIST.getPrefix()
     *
     * @param mbUser 实体
     * @return true or false
     */
    @Override
    public boolean register(MbUser mbUser) throws JsonProcessingException {
        MbUser user = mbUser.setNickname(DefaultUserInfoEnum.NICKNAME.getMessage());
        log.info("生成的nickname为==>{}", user.getNickname());
        MD5 md5 = MD5.create();
        String digestHex16 = md5.digestHex16(mbUser.getPassword());
        mbUser.setPassword(digestHex16);
        int result = mbUserMapper.insert(mbUser);
        redisUtil.remove(RedisPrefix.USER_TEMP.getPrefix() + mbUser.getUsername());
        log.info("从Redis移除了key==>{}", RedisPrefix.USER_TEMP.getPrefix() + mbUser.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        redisUtil.set(RedisPrefix.USER_EXIST.getPrefix() + mbUser.getUsername(), objectMapper.writeValueAsString(mbUser));
        log.info("用户 {} 持久化进Redis成功！", mbUser.getUsername());
        return result > 0;
    }

    @Override
    public boolean login(MbUser user) {
        return false;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param uid 主键
     * @return 实例对象
     */
    @Override
    public MbUser queryById(Long uid) {
        return this.mbUserMapper.queryById(uid);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param mbUser 实例对象
     * @return 对象列表
     */
    @Override
    public List<MbUser> queryAll(MbUser mbUser) {
        return null;
    }

    /**
     * 新增数据
     *
     * @param mbUser 实例对象
     * @return 实例对象
     */
    @Override
    public MbUser insert(MbUser mbUser) {
        this.mbUserMapper.insert(mbUser);
        return mbUser;
    }

    /**
     * 修改数据
     *
     * @param mbUser 实例对象
     * @return 实例对象
     */
    @Override
    public MbUser update(MbUser mbUser) {
        this.mbUserMapper.update(mbUser);
        return this.queryById(mbUser.getUid());
    }

    /**
     * 通过主键删除数据
     *
     * @param uid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long uid) {
        return this.mbUserMapper.deleteById(uid) > 0;
    }
}