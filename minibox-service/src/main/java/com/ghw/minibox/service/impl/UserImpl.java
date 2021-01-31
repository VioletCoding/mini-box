package com.ghw.minibox.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.GenerateBean;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.component.QiNiuUtil;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.MapperUtils;
import com.ghw.minibox.mapper.MbUserMapper;
import com.ghw.minibox.service.CommonService;
import com.ghw.minibox.utils.*;
import com.nimbusds.jose.JOSEException;
import com.qiniu.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author Violet
 * @description
 * @date 2021/1/5
 */
@Service
@Slf4j
@Deprecated
public class UserImpl implements CommonService<MbUser> {
    @Resource
    private SendEmail sendEmail;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private MbUserMapper userMapper;
    @Resource
    private MapperUtils mapperUtils;
    @Resource
    private QiNiuUtil qiNiuUtil;
    @Resource
    private NimbusJoseJwt jwt;
    @Resource
    private GenerateBean generateBean;

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

    @AopLog("发送邮件")
    @Async
    public void sendEmail(String username, String subject, String msg) throws EmailException, JsonProcessingException {
        //小写用户名
        String lowerCase = username.toLowerCase();
        //获取自动生成的验证码
        String authCode = generateAuthCode();
        //发送邮件
        sendEmail.createEmail(lowerCase, subject, msg + authCode);
        Map<String, Object> map = new HashMap<>();
        map.put("data", authCode);
        String json = generateBean.getObjectMapper().writeValueAsString(map);
        redisUtil.set(RedisUtil.AUTH_PREFIX + username, json, 300);
    }

    @AopLog("用户名查重")
    public boolean exist(String username) throws JsonProcessingException {
        //把传进来的邮箱格式化一下，统一小写
        String lowerCase = username.toLowerCase();
        List<MbUser> mbUser = userMapper.queryAll(new MbUser().setUsername(lowerCase));
        if (mbUser.size() != 0) {
            ObjectMapper om = generateBean.getObjectMapper();
            String json = om.writeValueAsString(mbUser.get(0));
            redisUtil.set(RedisUtil.LOGIN_FLAG + username, json, 300);
            return true;
        }
        return false;
    }

    @AopLog("对应不同业务发送不同邮件")
    public void service(String username) throws EmailException, JsonProcessingException {
        String authCode = generateAuthCode();
        String lowerCaseUsername = username.toLowerCase();
        Map<String, Object> map = new HashMap<>();
        map.put("data", authCode);
        try {
            //如果用户存在，执行登陆逻辑，否则执行注册逻辑
            if (exist(lowerCaseUsername)) {
                sendEmail.createEmail(lowerCaseUsername, SendEmail.SUBJECT, SendEmail.LOGIN_MESSAGE + authCode);
            } else {
                sendEmail.createEmail(lowerCaseUsername, SendEmail.SUBJECT, SendEmail.REGISTER_MESSAGE + authCode);
            }
        } finally {
            ObjectMapper om = generateBean.getObjectMapper();
            String json = om.writeValueAsString(map);
            redisUtil.set(RedisUtil.AUTH_PREFIX + lowerCaseUsername, json, 300L);
        }
    }

    @AopLog("校验验证码")
    public boolean authRegCode(String key, String code) throws JsonProcessingException {
        String valueFromRedis = redisUtil.get(RedisUtil.AUTH_PREFIX + key.toLowerCase());
        if (!StringUtils.isNullOrEmpty(valueFromRedis)) {
            Map<String, Object> map = generateBean.getObjectMapper().readValue(valueFromRedis, new TypeReference<Map<String, Object>>() {
            });
            boolean b = code.equals(map.get("data"));
            if (b) {
                redisUtil.remove(RedisUtil.AUTH_PREFIX + key);
                return true;
            }
        }
        throw new MiniBoxException("验证码已过期");
    }


    @AopLog("自动判断登陆或注册逻辑方法")
    @Transactional(rollbackFor = Throwable.class)
    public Object doService(String username, String authCode) throws JsonProcessingException, JOSEException {
        String lowerCaseUsername = username.toLowerCase();
        //先校验验证码
        boolean auth = authRegCode(lowerCaseUsername, authCode);
        if (auth) {
            //默认注册的角色 「用户」
            List<String> authorities = new ArrayList<>();
            authorities.add(UserRole.USER.getRole());
            //token的有效载荷，过期时间是一周
            PayloadDto payloadDto = jwt.buildToken(username, 604800L, authorities);
            //生成token
            String token = jwt.generateTokenByHMAC(payloadDto);
            try {
                /*如果是已经存在的用户，则返回token和非敏感信息
                  因为当前需求是要一起返回token信息，但是MbUser实体没这个字段，所以转成map，put一个token字段进去
                 */
                ObjectMapper objectMapper = generateBean.getObjectMapper();
                if (exist(username)) {
                    String json = redisUtil.get(RedisUtil.LOGIN_FLAG + username);
                    Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
                    });
                    map.put("token", token);
                    return map;
                }
                //如果不存在，则自动注册，并且注册成功后也返回token和非敏感信息
                MbUser mbUser = new MbUser();
                mbUser.setUsername(username)
                        .setNickname(DefaultColumn.NICKNAME.getMessage())
                        .setPassword(IdUtil.fastSimpleUUID());
                        //.setUserImg(QiNiuUtil.defaultPhotoLink);
                int insert = userMapper.insert(mbUser);
                int setUserRole = mapperUtils.setUserRole(UserRole.USER.getRoleId(), mbUser.getId());
                if (insert == 0 || setUserRole == 0) throw new MiniBoxException("注册时出现异常");
                //用于返回，因为当前需求是要一起返回token信息，但是MbUser实体没这个字段，所以转成map，put一个token字段进去
                MbUser newUser = userMapper.queryById(mbUser.getId());
                Map<String, Object> map = objectMapper.convertValue(newUser, new TypeReference<Map<String, Object>>() {
                });
                map.put("token", token);
                return map;
            } finally {
                redisUtil.set(RedisUtil.TOKEN_PREFIX + username, token, payloadDto.getExp());
                List<String> remove = new ArrayList<>();
                remove.add(RedisUtil.AUTH_PREFIX + lowerCaseUsername);
                remove.add(RedisUtil.LOGIN_FLAG + lowerCaseUsername);
                redisUtil.remove(remove);
            }
        }
        throw new MiniBoxException("验证码不合法");
    }

    @Override
    public List<MbUser> selectAll(MbUser param) {
        return userMapper.queryAll(param);
    }


    @Override
    public MbUser selectOne(Long id) {
        return userMapper.queryById(id);
    }

    @AopLog("查询个人信息，包括游戏信息，游戏数量")
    public Object showUserInfo(Long id) {
        //用户信息
        MbUser mbUser = userMapper.queryById(id);
        //游戏信息（包含游戏数量）
        List<Map<String, Object>> gameInfo = mapperUtils.countGameNum(id);
        Map<String, Object> map = new HashMap<>();
        map.put("mbUser", mbUser);
        map.put("gameList", gameInfo);
        return map;
    }

    @Override
    public boolean insert(MbUser entity) {
        return userMapper.insert(entity) > 0;
    }

    @AopLog("更新用户信息")
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean update(MbUser entity) {
        int update = userMapper.update(entity);
        if (update > 0) {
            redisUtil.remove(RedisUtil.REDIS_PREFIX + RedisUtil.USER_PREFIX + entity.getId());
            return true;
        }
        return false;
    }

    @AopLog("更新用户头像")
    @Transactional(rollbackFor = Throwable.class)
    public MbUser updateUserImg(MultipartFile file, Long uid) throws IOException {
        String fastSimpleUUID = IdUtil.fastSimpleUUID();
        String link = qiNiuUtil.syncUpload(fastSimpleUUID, file.getBytes());
        //更新用户信息
        boolean update = this.update(new MbUser().setId(uid));
        if (update)
            return this.selectOne(uid);
        throw new MiniBoxException("更新用户头像出现错误");
    }

    @AopLog("更新密码，id、password必传")
    @Transactional(rollbackFor = Throwable.class)
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

    @AopLog("登出")
    public void logout(String key) {
        redisUtil.remove(RedisUtil.TOKEN_PREFIX + key.toLowerCase());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean delete(Long id) {
        int i = userMapper.deleteById(id);
        int j = mapperUtils.deleteUserRole(id);
        return i > 0 && j > 0;
    }
}
