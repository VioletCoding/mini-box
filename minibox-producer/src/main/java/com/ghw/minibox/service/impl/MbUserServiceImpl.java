package com.ghw.minibox.service.impl;


import cn.hutool.crypto.digest.MD5;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.entity.MbPhoto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.mapper.MbPhotoMapper;
import com.ghw.minibox.mapper.MbUserMapper;
import com.ghw.minibox.service.MbUserService;
import com.ghw.minibox.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
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
    private MbPhotoMapper mbPhotoMapper;
    @Resource
    private SendEmail sendEmail;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private NimbusJoseJwt jwt;
    @Resource
    private QiNiuUtil qiNiuUtil;
    @Value("${qiNiu.defaultPhoto}")
    private String defaultLink;

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
     * 异步方法
     * 发送邮件，并写入Redis
     * <p>
     * 如果queryByUsername校验通过，那么可以往下执行这个方法
     * <p>
     * 该方法会生成一个6位数的随机验证码，并且放到邮件内容发送给注册的用户
     * <p>
     * 验证码会放进Redis里保存300秒钟，300秒后自动失效
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
        sendEmail.createEmail(username, subject, "尊敬的迷你盒用户，您本次的验证码为\n" + sb.toString() + "\n本次验证码有效时间为5分钟！");
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

    @Override
    public boolean upload(String ak, String sk, String bucket, InputStream inputStream) {
        return false;
    }

    /**
     * 必须带回滚
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
     * <p>
     * 之后会获取自增uid，并且set到MbPhoto表，图片与uid做映射关系，设置默认用户头像
     *
     * @param mbUser 实体
     * @return true or false
     */
    @Transactional
    @Override
    public boolean register(MbUser mbUser) throws JsonProcessingException {
        MbUser user = mbUser.setNickname(DefaultUserInfoEnum.NICKNAME.getMessage());
        log.info("生成的nickname为==>{}", user.getNickname());
        MD5 md5 = MD5.create();
        String digestHex16 = md5.digestHex16(mbUser.getPassword());
        mbUser.setPassword(digestHex16);
        mbUser.setUserState(UserStatus.NORMAL.getMessage());
        int result = mbUserMapper.insert(mbUser);
        mbPhotoMapper.insert(new MbPhoto().setType(PostType.PHOTO_USER.getType()).setLink(defaultLink).setUid(mbUser.getUid()));
        redisUtil.remove(RedisPrefix.USER_TEMP.getPrefix() + mbUser.getUsername());
        log.info("从Redis移除了key==>{}", RedisPrefix.USER_TEMP.getPrefix() + mbUser.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        redisUtil.set(RedisPrefix.USER_EXIST.getPrefix() + mbUser.getUsername(), objectMapper.writeValueAsString(mbUser));
        log.info("用户 {} 持久化进Redis成功！", mbUser.getUsername());
        return result > 0;
    }

    /**
     * 登陆方法，用于用户登陆
     * <p>
     * 首先从Redis获取用户的持久化信息
     * <p>
     * -如果获取到了：通过ObjectMapper转成对象，比对加密后的pwd是否一致，一致返回ResultCode.OK.getMessage()
     * -否则：通过username查找MySQL
     * <p>
     * --如果没有结果：返回ResultCode.NOT_FOUND.getMessage()
     * <p>
     * --如果有结果：同步数据到Redis，比对加密后的密码是否一致，以及用户状态是否为UserStatus.NORMAL.getStatus()
     * ---true：返回ResultCode.OK.getMessage()
     * ---false：返回ResultCode.NOT_FOUND.getMessage()
     * ---false：如果状态不为UserStatus.NORMAL.getStatus()返回ResultCode.USER_ILLEGAL.getMessage()
     * <p>
     * 以上情况都不符合，返回ResultCode.NOT_FOUND.getMessage()
     *
     * @param user 用户实体
     * @return ResultCode
     * @throws JsonProcessingException Json解析失败
     */
    @Override
    public String login(MbUser user) throws JsonProcessingException {
        String rs = redisUtil.get(RedisPrefix.USER_EXIST.getPrefix() + user.getUsername());
        log.info("从Redis获取到的结果为==>{}", rs);
        MbUser json2Object;
        ObjectMapper objectMapper;
        MD5 md5 = new MD5();

        if (rs != null && !rs.equals("")) {
            objectMapper = new ObjectMapper();
            json2Object = objectMapper.readValue(rs, MbUser.class);
            log.info("解析json为对象结果==>{}", json2Object);
            if (json2Object.getPassword() != null && !json2Object.getPassword().equals("")) {
                String hex16Pwd = md5.digestHex16(user.getPassword());
                if (hex16Pwd.equals(json2Object.getPassword()) && json2Object.getUserState().equals(UserStatus.NORMAL.getMessage())) {
                    return ResultCode.OK.getMessage();
                } else {
                    return ResultCode.USER_ILLEGAL.getMessage();
                }
            }
        }

        if (!redisUtil.exist(RedisPrefix.USER_EXIST.getPrefix() + user.getUsername())) {
            log.info("未能从Redis获取到用户信息，准备查找MySQL...");
            MbUser mbUser = mbUserMapper.queryByUsername(user.getUsername());
            if (mbUser == null) {
                log.info("未能从MySQL获取到用户信息，用户未注册！");
                return ResultCode.NOT_FOUND.getMessage();
            }
            objectMapper = new ObjectMapper();
            redisUtil.set(RedisPrefix.USER_EXIST.getPrefix() + mbUser.getUsername(), objectMapper.writeValueAsString(mbUser));
            log.info("检测Redis与MySQL状态不同步，现已执行同步方法");
            String pwd = mbUser.getPassword();
            String hex16Pwd = md5.digestHex16(user.getPassword());
            if (pwd.equals(hex16Pwd) && mbUser.getUserState().equals(UserStatus.NORMAL.getStatus()))
                return ResultCode.OK.getMessage();
        }
        return ResultCode.NOT_FOUND.getMessage();
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