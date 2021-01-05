package com.ghw.minibox.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.entity.MbPhoto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.mapper.MbPhotoMapper;
import com.ghw.minibox.mapper.MbUserMapper;
import com.ghw.minibox.service.CommonService;
import com.ghw.minibox.utils.*;
import com.nimbusds.jose.JOSEException;
import com.qiniu.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Violet
 * @description
 * @date 2021/1/5
 */
@Service
@Slf4j
public class UserImpl implements CommonService<MbUser> {
    //TODO 这个类需要大改
    @Resource
    private SendEmail sendEmail;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private MbUserMapper userMapper;
    @Resource
    private MbPhotoMapper photoMapper;
    @Resource
    private NimbusJoseJwt jwt;
    @Value("${qiNiu.defaultPhoto}")
    private String defaultLink;

    @AOPLog("生成验证码")
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

    @AOPLog("发送邮件")
    @Async
    public void sendEmail(String username, String subject, String msg) throws EmailException, JsonProcessingException {
        String lowerCase = username.toLowerCase();
        String authCode = generateAuthCode();
        sendEmail.createEmail(lowerCase, subject, msg + authCode);
        Map<String, Object> map = new HashMap<>();
        map.put("data", authCode);
        String json = new ObjectMapper().writeValueAsString(map);
        redisUtil.set(RedisUtil.AUTH_PREFIX + username, json, 300L);
    }

    @AOPLog("用户名查重")
    public boolean exist(String username) throws JsonProcessingException {
        //把传进来的邮箱格式化一下，统一小写
        String lowerCase = username.toLowerCase();
        List<MbUser> mbUser = userMapper.queryAll(new MbUser().setUsername(lowerCase));
        if (mbUser.get(0) != null) {
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(mbUser.get(0));
            log.info("用户存在，存到Redis=>{}", json);
            redisUtil.set(RedisUtil.LOGIN_FLAG + username, json, 300L);
            return true;
        } else {
            return false;
        }
    }

    @AOPLog("对应不同业务发送不同邮件")
    public void service(String username) throws EmailException, JsonProcessingException {
        String authCode = generateAuthCode();
        log.info("验证码是=>{}", authCode);
        String lowerCaseUsername = username.toLowerCase();
        log.info("小写后的username是=>{}", lowerCaseUsername);
        Map<String, Object> map = new HashMap<>();
        map.put("data", authCode);
        log.info("组装后的DataDto=>{}", map.toString());
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
            String json = om.writeValueAsString(map);
            log.info("无论如何，把验证码存到redis里=>{}", json);
            redisUtil.set(RedisUtil.AUTH_PREFIX + lowerCaseUsername, json, 300L);
        }
    }

    @AOPLog("校验验证码")
    public boolean authRegCode(String key, String code) throws JsonProcessingException {
        log.info("打印一下key=>{}和authCode=>{}", key, code);
        String valueFromRedis = redisUtil.get(RedisUtil.AUTH_PREFIX + key.toLowerCase());
        log.info("校验验证码,从Redis获取的值=>{}", valueFromRedis);

        if (!StringUtils.isNullOrEmpty(valueFromRedis)) {
            Map<String, Object> map = new ObjectMapper().readValue(valueFromRedis, new TypeReference<Map<String, Object>>() {
            });
            boolean b = code.equals(map.get("data"));
            if (b) {
                redisUtil.remove(RedisUtil.AUTH_PREFIX + key);
                return true;
            }
        }
        return false;
    }


    @AOPLog("自动判断登陆或注册逻辑方法")
    @Transactional(rollbackFor = Exception.class)
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
                userMapper.insert(mbUser);
                //默认头像
                photoMapper.insert(new MbPhoto().setPhotoLink(this.defaultLink).setType("UP").setUid(mbUser.getId()));
                //用于返回
                log.info("测试一下是否能获取自增uid=>{}", mbUser.getId());
                mbUser.setMbPhoto(new MbPhoto().setPhotoLink(this.defaultLink).setType("UP").setUid(mbUser.getId()));
                mbUser.setToken(token);
                Map<String, Object> map = new HashMap<>();
                map.put("code", ResultCode.OK.getCode());
                map.put("message", ResultCode.OK.getMessage());
                map.put("data", mbUser);
                return map;
            } finally {
                redisUtil.set(RedisUtil.TOKEN_PREFIX + username, token, payloadDto.getExp());
                List<String> remove = new ArrayList<>();
                remove.add(RedisUtil.AUTH_PREFIX + lowerCaseUsername);
                remove.add(RedisUtil.LOGIN_FLAG + lowerCaseUsername);
                redisUtil.remove(remove);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", ResultCode.AUTH_CODE_ERROR.getCode());
        map.put("message", ResultCode.AUTH_CODE_ERROR.getMessage());
        return map;
    }

    @Override
    public List<MbUser> selectAll(MbUser param) {
        return null;
    }

    @Override
    public MbUser selectOne(Long id) {
        return userMapper.queryById(id);
    }

    @Override
    public boolean insert(MbUser entity) {
        return userMapper.insert(entity) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(MbUser entity) {
        int update = userMapper.update(entity);
        return update > 0;
    }

    @AOPLog("更新密码，id、password必传")
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(MbUser mbUser) {
        String md5 = SecureUtil.md5(mbUser.getPassword());
        mbUser.setPassword(md5);
        int update = userMapper.update(mbUser);
        if (update > 0) {
            redisUtil.remove(RedisUtil.TOKEN_PREFIX + this.selectOne(mbUser.getId()).getUsername());
            return true;
        }
        return false;
    }

    @AOPLog("登出")
    public void logout(String key) {
        redisUtil.remove(RedisUtil.TOKEN_PREFIX + key.toLowerCase());
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
