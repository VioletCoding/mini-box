package com.ghw.minibox.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.mapper.MbPhotoMapper;
import com.ghw.minibox.mapper.MbUserMapper;
import com.ghw.minibox.service.MbUserService;
import com.ghw.minibox.utils.RedisPrefix;
import com.ghw.minibox.utils.ResultCode;
import com.ghw.minibox.utils.SendEmail;
import com.ghw.minibox.utils.UserRole;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * 生成验证码
     *
     * @param num 验证码个数
     * @return 验证码
     */
    private String generateAuthCode(int num) throws Exception {
        if (num <= 0) {
            throw new Exception("验证码个数小于等于0");
        }
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int randomNum;
        for (int i = 0; i < 6; i++) {
            randomNum = random.nextInt(10);
            sb.append(randomNum);
        }
        return sb.toString();
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
    public void sendEmail(String username, String subject, String msg, String code) throws EmailException {
        String lowerCase = username.toLowerCase();
        sendEmail.createEmail(lowerCase, subject, msg + code);
    }

    /**
     * 如果返回的对象不为空，就证明邮箱username已经被占用，此时可以直接登陆，返回的验证码则用于登陆
     * <p>
     * 从Redis检查是否已经有该用户，看看是否已发送过验证码，如果发送过了，300秒后才能再次请求
     * <p>
     * 如果用户已经持久化，也就是注册成功了，直接返回用户已存在
     *
     * @param username 邮箱
     * @return 实体
     */
    @Override
    public boolean exist(String username) throws Exception {
        String lowerCase = username.toLowerCase();
        MbUser mbUser = mbUserMapper.queryByUsername(lowerCase);
        String authCode = generateAuthCode(6);

        if (mbUser == null) {
            sendEmail(lowerCase, SendEmail.SUBJECT, SendEmail.REGISTER_MESSAGE, authCode);
        } else {
            sendEmail(lowerCase, SendEmail.SUBJECT, SendEmail.LOGIN_MESSAGE, authCode);
        }
        redisUtil.set(RedisPrefix.USER_TEMP.getPrefix() + lowerCase, authCode);
        redisUtil.expire(RedisPrefix.USER_TEMP.getPrefix() + lowerCase, 300L);
        return true;
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
        return value.equals(valueFromRedis);
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
        //TODO
        //token放在网关校验
        //登录逻辑要重新想
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
}