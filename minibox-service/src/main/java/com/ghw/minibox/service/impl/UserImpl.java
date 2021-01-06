package com.ghw.minibox.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.mapper.MapperUtils;
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
    @Value("${qiNiu.defaultPhoto}")
    private String defaultLink;
    @Value("${qiNiu.link}")
    private String qiNiuYunLink;

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
        //小写用户名
        String lowerCase = username.toLowerCase();
        //获取自动生成的验证码
        String authCode = generateAuthCode();
        //发送邮件
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

        if (mbUser.size() != 0) {
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
        String lowerCaseUsername = username.toLowerCase();
        Map<String, Object> map = new HashMap<>();
        map.put("data", authCode);
        try {
            //如果用户存在，执行登陆逻辑，否则执行注册逻辑
            if (exist(lowerCaseUsername)) {
                log.info("用户存在，发送「登陆」验证码短信");
                sendEmail.createEmail(lowerCaseUsername, SendEmail.SUBJECT, SendEmail.LOGIN_MESSAGE + authCode);
            } else {
                log.info("用户不存在，发送「注册」验证码短信");
                sendEmail.createEmail(lowerCaseUsername, SendEmail.SUBJECT, SendEmail.REGISTER_MESSAGE + authCode);
            }
        } finally {
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(map);
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
        log.info("打印doService入参=>{} => {}", username, authCode);
        String lowerCaseUsername = username.toLowerCase();
        log.info("小写后的username=>{}", lowerCaseUsername);
        //先校验验证码
        boolean auth = authRegCode(lowerCaseUsername, authCode);

        if (auth) {
            log.info("验证码校验通过");
            //TODO 权限列表记得要加上，现在这里是写死
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
                        .setNickname(DefaultUserInfoEnum.NICKNAME.getMessage())
                        .setPassword(IdUtil.fastSimpleUUID())
                        .setUserImg(this.defaultLink);
                userMapper.insert(mbUser);
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
        throw new RuntimeException("执行自动注册和登陆方法出错");
    }

    @Override
    public List<MbUser> selectAll(MbUser param) {
        return null;
    }


    @Override
    public MbUser selectOne(Long id) {
        return userMapper.queryById(id);
    }

    @AOPLog("查询个人信息，包括游戏信息，游戏数量")
    public Object showUserInfo(Long id) throws JsonProcessingException {
        ObjectMapper objectMapper = generateBean.getObjectMapper();
        //先查缓存
        String fromRedis = redisUtil.get(RedisUtil.REDIS_PREFIX + RedisUtil.USER_PREFIX + id);
        if (!StringUtils.isNullOrEmpty(fromRedis)) {
            return objectMapper.readValue(fromRedis, new TypeReference<Map<String, Object>>() {
            });
        }
        //用户信息
        MbUser mbUser = userMapper.queryById(id);
        //游戏信息（包含游戏数量）
        List<Map<String, Object>> gameInfo = mapperUtils.countGameNum(id);

        Map<String, Object> map = objectMapper.convertValue(mbUser, new TypeReference<Map<String, Object>>() {
        });
        //将游戏信息一起返回
        map.put("gameList", gameInfo);
        redisUtil.set(RedisUtil.REDIS_PREFIX + RedisUtil.USER_PREFIX + id, objectMapper.writeValueAsString(map), 86400L);
        return map;
    }

    @Override
    public boolean insert(MbUser entity) {
        return userMapper.insert(entity) > 0;
    }

    @AOPLog("更新用户信息")
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

    @AOPLog("更新用户头像")
    @Transactional(rollbackFor = Throwable.class)
    public MbUser updateUserImg(MultipartFile file, Long uid) throws IOException {

        String fastSimpleUUID = IdUtil.fastSimpleUUID();

        qiNiuUtil.syncUpload(fastSimpleUUID, file.getBytes());
        //更新用户信息
        boolean update = this.update(new MbUser().setUserImg(this.qiNiuYunLink + fastSimpleUUID).setId(uid));

        if (update) {
            return this.selectOne(uid);
        }
        throw new RuntimeException("更新用户头像出现错误");
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
