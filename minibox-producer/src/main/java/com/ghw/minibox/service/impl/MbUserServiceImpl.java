package com.ghw.minibox.service.impl;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.GenerateResult;
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
     * @return 验证码
     */
    private String generateAuthCode() {
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
     * 发送邮件，异步方法
     *
     * @param username 邮箱
     * @param subject  邮件主题
     * @param msg      邮件内容
     * @param code     验证码
     */
    @Async
    @Override
    public void sendEmail(String username, String subject, String msg, String code) throws EmailException {
        String lowerCase = username.toLowerCase();
        log.info("发送邮件=>{}", lowerCase);
        sendEmail.createEmail(lowerCase, subject, msg + code);
    }


    /**
     * 通过username（邮箱）进行校验用户是否存在
     *
     * @param username 邮箱
     */
    @Override
    public boolean exist(String username) throws JsonProcessingException {
        //把传进来的邮箱格式化一下，统一小写
        String lowerCase = username.toLowerCase();
        log.info("小写=>{}", lowerCase);
        MbUser mbUser = mbUserMapper.queryByUsername(lowerCase);
        log.info("打印一下mbUser=>{}", mbUser);
        if (mbUser != null) {
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(mbUser);
            log.info("用户存在，存到Redis=>{}", json);
            redisUtil.set(RedisUtil.LOGIN_FLAG + username, json, 300L);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 主要业务逻辑，如果用户存在，执行登陆，如果用户不存在，执行注册，本质是发送两封内容不一样的邮件
     * 验证码一定要存进redis
     */
    @Override
    public void service(String username) throws EmailException, JsonProcessingException {
        String authCode = generateAuthCode();
        log.info("验证码是=>{}", authCode);
        String lowerCaseUsername = username.toLowerCase();
        log.info("小写后的username是=>{}", lowerCaseUsername);
        DataDto build = new DataDto().setData(authCode);
        log.info("组装后的DataDto=>{}", build.toString());
        try {
            //如果用户存在，执行登陆逻辑，否则执行注册逻辑
            if (exist(username)) {
                log.info("用户存在，发送「登陆」验证码短信");
                sendEmail.createEmail(lowerCaseUsername, SendEmail.SUBJECT, SendEmail.LOGIN_MESSAGE + authCode);
            } else {
                log.info("用户不存在，发送「注册」验证码短信");
                sendEmail.createEmail(lowerCaseUsername, SendEmail.SUBJECT, SendEmail.REGISTER_MESSAGE + authCode);
            }
        } finally {
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(build);
            log.info("无论如何，把验证码存到redis里=>{}", json);
            redisUtil.set(RedisUtil.AUTH_PREFIX + lowerCaseUsername, json, 300L);
        }
    }

    /**
     * 校验 验证码
     * <p>
     * 校验传入的验证码是否跟在Redis保存的验证码一致
     *
     * @param key      邮箱
     * @param authCode 验证码
     * @return true or false
     */
    @Override
    public boolean authRegCode(String key, String authCode) throws JsonProcessingException {
        log.info("打印一下key=>{}和authCode=>{}", key, authCode);
        String valueFromRedis = redisUtil.get(RedisUtil.AUTH_PREFIX + key.toLowerCase());
        log.info("校验验证码,从Redis获取的值=>{}", valueFromRedis);
        DataDto dataDto;
        if (valueFromRedis != null && !valueFromRedis.equals("")) {
            dataDto = new ObjectMapper().readValue(valueFromRedis, DataDto.class);
            log.info("校验验证码=>{}", dataDto);
            return authCode.equals(dataDto.getData());
        }
        return false;
    }


    /**
     * 主要逻辑方法
     * 这其实是一个复合接口，如果用户存在，执行登陆，如果用户不存在，执行自动注册方法
     *
     * @param username 邮箱
     * @param authCode 验证码
     * @return 用户非敏感信息
     */
    @Override
    public Object doService(String username, String authCode) throws JsonProcessingException, JOSEException {
        String lowerCaseUsername = username.toLowerCase();
        //先校验验证码
        boolean auth = authRegCode(lowerCaseUsername, authCode);

        if (auth) {
            //权限
            //TODO 权限列表记得要加上，现在这里是写死
            List<String> authorities = new ArrayList<>();
            authorities.add(UserRole.USER.getRole());
            //token的有效载荷，过期时间是一周
            PayloadDto payloadDto = jwt.buildToken(username, 604800L, authorities);
            //生成token
            String token = jwt.generateTokenByHMAC(payloadDto);
            try {
                //如果是已经存在的用户，则返回token和非敏感信息
                if (exist(username)) {
                    String json = redisUtil.get(RedisUtil.LOGIN_FLAG + username);
                    ObjectMapper om = new ObjectMapper();
                    MbUser mbUser = om.readValue(json, MbUser.class);
                    mbUser.setToken(token);
                    return new GenerateResult<>().success(mbUser);
                }
                //如果不存在，则自动注册，并且注册成功后也返回token和非敏感信息
                MbUser mbUser = new MbUser();
                mbUser.setUsername(username)
                        .setNickname(DefaultUserInfoEnum.NICKNAME.getMessage())
                        .setPassword(IdUtil.fastSimpleUUID());
                mbUserMapper.insert(mbUser);
                //默认头像
                mbPhotoMapper.insert(new MbPhoto().setPhotoLink(this.defaultLink).setType("UP").setUid(mbUser.getUid()));
                //用于返回
                log.info("测试一下是否能获取自增uid=>{}", mbUser.getUid());
                mbUser.setMbPhoto(new MbPhoto().setPhotoLink(this.defaultLink).setType("UP").setUid(mbUser.getUid()));
                mbUser.setToken(token);
                return new DataDto()
                        .setCode(ResultCode.OK.getCode())
                        .setMessage(ResultCode.OK.getMessage()).setData(mbUser);
            } finally {
                redisUtil.set(RedisUtil.TOKEN_PREFIX + username, token, payloadDto.getExp());
                List<String> remove = new ArrayList<>();
                remove.add(RedisUtil.AUTH_PREFIX + lowerCaseUsername);
                remove.add(RedisUtil.LOGIN_FLAG + lowerCaseUsername);
                redisUtil.remove(remove);
            }
        }
        return new DataDto()
                .setCode(ResultCode.AUTH_CODE_ERROR.getCode())
                .setMessage(ResultCode.AUTH_CODE_ERROR.getMessage());
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