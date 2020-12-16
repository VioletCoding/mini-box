package com.ghw.minibox.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.entity.MbPhoto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.mapper.MbPhotoMapper;
import com.ghw.minibox.mapper.MbUserMapper;
import com.ghw.minibox.service.MbUserService;
import com.ghw.minibox.utils.*;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
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
    public boolean queryByUsername(String username) throws EmailException {
        String lowerCase = username.toLowerCase();
        MbUser mbUser = mbUserMapper.queryByUsername(lowerCase);
        if (mbUser == null) {
            String subject = "Minibox验证码，请注意查收！";
            String msg = "您正在注册迷你盒，本次验证码5分钟内有效：";
            sendEmail(lowerCase, subject, msg);
            return true;
        }
        return false;
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
    public void sendEmail(String username, String subject, String msg) throws EmailException {
        String lowerCase = username.toLowerCase();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int randomNum;
        for (int i = 0; i < 6; i++) {
            randomNum = random.nextInt(10);
            sb.append(randomNum);
        }
        sendEmail.createEmail(lowerCase, subject, msg + sb.toString());
        redisUtil.set(RedisPrefix.USER_TEMP.getPrefix() + lowerCase, sb.toString());
        redisUtil.expire(RedisPrefix.USER_TEMP.getPrefix() + lowerCase, 300L);
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
        String valueFromRedis = redisUtil.get(RedisPrefix.USER_TEMP.getPrefix() + key.toLowerCase());
        log.info("authRegCode==>{}", valueFromRedis);
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
    public boolean register(MbUser mbUser) {
        mbUser.setUsername(mbUser.getUsername().toLowerCase());
        mbUser.setNickname(DefaultUserInfoEnum.NICKNAME.getMessage());
        MD5 md5 = MD5.create();
        String digestHex16 = md5.digestHex16(mbUser.getPassword());
        mbUser.setPassword(digestHex16);
        int result = mbUserMapper.insert(mbUser);
        mbPhotoMapper.insert(new MbPhoto().setType(PostType.PHOTO_USER.getType()).setPhotoLink(defaultLink).setUid(mbUser.getUid()));
        if (result > 0) {
            redisUtil.remove(RedisPrefix.USER_TEMP.getPrefix() + mbUser.getUsername());
            return true;
        }
        return false;
    }

    /**
     * 登陆-查MySQL
     * <p>
     * 逻辑跟redis2login差不多，不过这是保底的方法了，再不行的话，用户就是查无此人
     *
     * @param mbUser 用户实体
     * @return ReturnDto
     */
    @Override
    public String login(MbUser mbUser) throws JsonProcessingException, JOSEException {
        MbUser fromMySQL = mbUserMapper.queryByUsername(mbUser.getUsername());
        if (fromMySQL == null) {
            return ResultCode.NOT_FOUND.getMessage();
        }
        MD5 md5 = new MD5();
        String hex16 = md5.digestHex16(mbUser.getPassword());
        if (fromMySQL.getPassword().equals(hex16)) {
            List<String> roleList = new ArrayList<>();
            roleList.add(UserRole.USER.getRole());
            PayloadDto payloadDto = jwt.buildToken(mbUser.getUsername(), 604800000L, roleList);
            return jwt.generateTokenByHMAC(payloadDto);
        }
        return ResultCode.USER_AUTH_FAIL.getMessage();
    }

    /**
     * 判断是否登陆
     *
     * @param token token
     * @return true or false
     */
    @Override
    public boolean logout(String token) throws Exception {
        if (token == null || token.equals("")) {
            return false;
        }
        PayloadDto payloadDto = jwt.verifyTokenByHMAC(token);
        log.info("payloadDto==>{}", payloadDto);
        Boolean exist = redisUtil.exist(RedisPrefix.USER_TOKEN.getPrefix() + payloadDto.getUsername());
        if (exist) {
            redisUtil.remove(RedisPrefix.USER_TOKEN.getPrefix() + payloadDto.getUsername());
            return true;
        }
        return false;
    }

    /**
     * 忘记密码校验
     *
     * @param mbUser 实体
     */
    @Override
    public boolean forgetPassword(MbUser mbUser) throws EmailException {
        MbUser queryByUsername = mbUserMapper.queryByUsername(mbUser.getUsername());
        if (queryByUsername == null) {
            return false;
        }
        String subject = "您正在重置迷你盒的密码";
        String msg = "您正在重置迷你盒的密码，有效期5分钟，验证码为：";
        sendEmail(mbUser.getUsername(), subject, msg);
        return true;
    }

    /**
     * 重置密码
     *
     * @param mbUser 实体
     * @return ReturnDto<String>
     */
    @Transactional
    @Override
    public boolean doResetPassword(MbUser mbUser) {
        MD5 md5 = new MD5();
        String hex16 = md5.digestHex16(mbUser.getPassword());
        mbUser.setPassword(hex16);
        int result = mbUserMapper.updatePassword(mbUser);
        redisUtil.remove(RedisPrefix.USER_TEMP.getPrefix() + mbUser.getUsername());
        return result > 0;
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