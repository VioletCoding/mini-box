package com.ghw.minibox.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.MbpUserMapper;
import com.ghw.minibox.model.RoleModel;
import com.ghw.minibox.model.UserModel;
import com.ghw.minibox.utils.AopLog;
import com.ghw.minibox.utils.DefaultColumn;
import com.ghw.minibox.utils.SendEmail;
import com.ghw.minibox.utils.UserRole;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Violet
 * @description
 * @date 2021/1/31
 */
@Service
@Slf4j
public class MbpUserServiceImpl {
    @Resource
    private MbpUserMapper mbpUserMapper;
    @Resource
    private SendEmail sendEmail;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private NimbusJoseJwt nimbusJoseJwt;

    /**
     * 校验这个用户名是否已经被注册
     *
     * @param username 邮箱
     * @return 存在返回true，不存在返回false
     */
    @AopLog("检查用户是否存在")
    public boolean exist(String username) {
        QueryWrapper<UserModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("username").eq("username", username);
        UserModel userModel = mbpUserMapper.selectOne(queryWrapper);
        return userModel != null;
    }

    /**
     * 生成6位数验证码
     *
     * @return 验证码
     */
    public String authCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i <= 5; i++) {
            int n = random.nextInt(9);
            sb.append(n);
        }
        return sb.toString();
    }

    /**
     * 发送验证码，根据用户是否存在发送对应的邮件内容
     * 统一会把用户名（邮箱）替换为小写
     * 验证码放进缓存里以便使用，过期时间为5分钟
     *
     * @param username 用户名
     */
    @AopLog("发送邮件")
    @Async
    public void sendAuthCode(String username) throws EmailException {
        String lowerCase = username.toLowerCase();
        String authCode = authCode();
        redisUtil.set(RedisUtil.AUTH_PREFIX + lowerCase, String.valueOf(authCode), 300L);
        if (exist(lowerCase)) {
            sendEmail.createEmail(lowerCase, SendEmail.SUBJECT, SendEmail.LOGIN_MESSAGE + authCode);
        } else {
            sendEmail.createEmail(lowerCase, SendEmail.SUBJECT, SendEmail.REGISTER_MESSAGE + authCode);
        }
    }

    /**
     * 先判断验证码是否正确，如果不正确那么抛出异常
     * 如果验证码正确，且用户已注册，true
     * 如果验证码正确，但用户未注册，自动注册，true
     *
     * @param username 邮箱
     * @param authCode 验证码
     */
    @AopLog("注册或登录")
    public boolean registerOrLogin(String username, String authCode) {
        String lowerCase = username.toLowerCase();
        String value = redisUtil.get(RedisUtil.AUTH_PREFIX + lowerCase);
        if (StrUtil.isBlank(value)) {
            return false;
        }
        if (value.equals(authCode)) {
            if (exist(lowerCase)) {
                return true;
            }
            UserModel userModel = new UserModel();
            userModel.setUserState(DefaultColumn.USER_STATE_NORMAL.getMessage());
            userModel.setPhotoLink(DefaultColumn.PHOTO_LINK.getMessage());
            userModel.setDescription(DefaultColumn.DESCRIPTION.getMessage());
            userModel.setPassword(DefaultColumn.PASSWORD.getMessage());
            userModel.setState(DefaultColumn.STATE.getMessage());
            userModel.setNickname(DefaultColumn.NICKNAME.getMessage());
            userModel.setUsername(lowerCase);
            int save = mbpUserMapper.insert(userModel);
            if (save > 0) {
                int i = mbpUserMapper.setUserRoles(userModel.getId(), UserRole.USER.getRoleId());
                return i > 0;
            } else {
                return false;
            }
        } else {
            throw new MiniBoxException("验证码不正确");
        }
    }

    @AopLog("执行service方法")
    public Map<String, Object> service(String username, String authCode) throws JsonProcessingException, JOSEException {
        boolean b = registerOrLogin(username, authCode);
        if (b) {
            //返回部分用户信息
            String lowerCase = username.toLowerCase();
            UserModel userModel = findOne("username", lowerCase);
            userModel.setPassword(null);
            userModel.setUserState(null);
            userModel.setState(null);
            userModel.setCreateDate(null);
            userModel.setUpdateDate(null);
            HashMap<String, Object> map = new HashMap<>();
            map.put("userInfo", userModel);
            //获取角色列表，为了组建token
            List<RoleModel> roles = mbpUserMapper.findUserRoles(userModel.getId());
            List<String> roleNameList = roles.stream().map(RoleModel::getName).collect(Collectors.toList());
            map.put("userRoles", roleNameList);
            //7天有效期
            PayloadDto payloadDto = nimbusJoseJwt.buildToken(lowerCase, 604800L, roleNameList);
            String token = nimbusJoseJwt.generateTokenByHMAC(payloadDto);
            //存储token,7天过期
            redisUtil.set(RedisUtil.TOKEN_PREFIX + lowerCase, token, 604800);
            map.put("token", token);
            //验证码使用过后及时删除
            redisUtil.remove(RedisUtil.AUTH_PREFIX + lowerCase);
            return map;
        } else {
            throw new MiniBoxException("验证码不正确");
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public boolean save(UserModel model) {
        int insert = mbpUserMapper.insert(model);
        return insert > 0;
    }

    public UserModel findOne(String column, Object value) {
        QueryWrapper<UserModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column, value);
        return mbpUserMapper.selectOne(queryWrapper);
    }

}
