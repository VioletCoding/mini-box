package com.ghw.minibox.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.component.QiNiuUtil;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.exception.MiniBoxException;
import com.ghw.minibox.mapper.*;
import com.ghw.minibox.model.*;
import com.ghw.minibox.utils.*;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Violet
 * @description 用户 逻辑层
 * @date 2021/1/31
 */
@Service
@Slf4j
public class MbpUserService {
    @Resource
    private MbpUserMapper mbpUserMapper;
    @Resource
    private MbpPostMapper mbpPostMapper;
    @Resource
    private MbpReplyMapper mbpReplyMapper;
    @Resource
    private MbpCommentMapper mbpCommentMapper;
    @Resource
    private SendEmail sendEmail;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private NimbusJoseJwt nimbusJoseJwt;
    @Resource
    private MbpOrderMapper mbpOrderMapper;

    /**
     * 校验这个用户名是否已经被注册
     *
     * @param username 邮箱
     * @return 存在返回true，不存在返回false
     */
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
    @AopLog("发送验证码")
    @Async
    public void sendAuthCode(String username) throws EmailException {
        String lowerCase = username.toLowerCase();
        String authCode = authCode();
        redisUtil.set(RedisUtil.AUTH_PREFIX + lowerCase, authCode, 300L);
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
    @Transactional(rollbackFor = Throwable.class)
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
            userModel.setPhotoLink(QiNiuUtil.defaultPhotoLink);
            userModel.setDescription(DefaultColumn.DESCRIPTION.getMessage());
            userModel.setPassword(DefaultColumn.PASSWORD.getMessage());
            userModel.setState(DefaultColumn.STATE.getMessage());
            userModel.setNickname(DefaultColumn.NICKNAME.getMessage());
            userModel.setUsername(lowerCase);
            int save = mbpUserMapper.insert(userModel);
            if (save > 0) {
                int i = mbpUserMapper.setUserRoles(userModel.getId(), UserRole.USER.getRoleId());
                return i > 0;
            }
            return false;
        }
        throw new MiniBoxException("验证码不正确");
    }

    public Map<String, Object> returnData(UserModel userModel) throws JsonProcessingException, JOSEException {
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", userModel);
        //获取角色列表，为了组建token
        List<RoleModel> roles = mbpUserMapper.findUserRoles(userModel.getId());
        List<String> roleNames = roles.stream().map(RoleModel::getName).collect(Collectors.toList());
        boolean adminFlag = roleNames.stream().anyMatch(role -> role.equals(UserRole.ADMIN.getRole()));
        map.put("adminFlag", adminFlag);
        //7天有效期
        String username = userModel.getUsername();
        Long id = userModel.getId();
        PayloadDto payloadDto = nimbusJoseJwt.buildToken(username, id, 604800L, roleNames);
        String token = nimbusJoseJwt.generateTokenByHMAC(payloadDto);
        //存储token,7天过期
        redisUtil.set(RedisUtil.TOKEN_PREFIX + username, token, 604800);
        map.put("token", token);
        return map;
    }

    @AopLog("service方法")
    public Map<String, Object> service(String username, String authCode) throws JsonProcessingException, JOSEException {
        boolean b = registerOrLogin(username, authCode);
        if (b) {
            //返回部分用户信息
            String lowerCase = username.toLowerCase();
            QueryWrapper<UserModel> userWrapper = new QueryWrapper<>();
            userWrapper.select("id", "nickname", "description", "photo_link", "nickname", "username")
                    .eq("username", lowerCase);
            UserModel userModel = mbpUserMapper.selectOne(userWrapper);
            Map<String, Object> map = this.returnData(userModel);
            //验证码使用过后及时删除
            Boolean remove = redisUtil.remove(RedisUtil.AUTH_PREFIX + userModel.getUsername());
            if (remove) {
                return map;
            }
            //一般不太可能走到这里
            for (int i = 0; i < 3; i++) {
                Boolean t = redisUtil.remove(RedisUtil.AUTH_PREFIX + userModel.getUsername());
                if (t) break;
            }
        }
        throw new MiniBoxException(ResultCode.AUTH_CODE_ERROR.getMessage());
    }

    /**
     * 用户名或密码登陆
     *
     * @param username 用户名
     * @param password 密码
     */
    public Map<String, Object> usingPasswordLogin(String username, String password) throws JsonProcessingException, JOSEException {
        String md5 = SecureUtil.md5(password);
        QueryWrapper<UserModel> wrapper = new QueryWrapper<>();
        wrapper.select("password", "id", "nickname", "description", "photo_link", "username")
                .eq("username", username);
        UserModel userModel = mbpUserMapper.selectOne(wrapper);
        if (userModel == null) {
            throw new MiniBoxException("用户名不存在");
        }
        if (userModel.getPassword().equals(md5)) {
            userModel.setPassword(null);
            return returnData(userModel);
        }
        throw new MiniBoxException("密码不正确");
    }

    /**
     * 查找用户的详细信息，在 「我」的页面
     *
     * @param id 用户ID
     */
    public UserModel findUserDetail(Long id) {
        return mbpUserMapper.findUserDetail(id);
    }

    /**
     * 查找该用户发布的所有帖子
     *
     * @param id 用户id
     */
    public List<PostModel> findUserAllPost(Long id) {
        //帖子信息
        QueryWrapper<PostModel> postWrapper = new QueryWrapper<>();
        postWrapper.select("id", "title", "photo_link")
                .eq("author_id", id)
                .orderByDesc("create_date");
        return mbpPostMapper.selectList(postWrapper);
    }

    /**
     * 查找该用户发表的所有评论 以及在哪个帖子下发的评论
     *
     * @param id 用户id
     */
    public List<CommentModel> findUserAllComment(Long id) {
        return mbpCommentMapper.findCommentAndLocationByUserId(id);
    }

    /**
     * 修改用户个人信息
     *
     * @param userModel 实体
     */
    @Transactional(rollbackFor = Throwable.class)
    public UserModel updateUserInfo(UserModel userModel) {
        UpdateWrapper<UserModel> userWrapper = new UpdateWrapper<>();
        userWrapper.eq("id", userModel.getId());
        int update = mbpUserMapper.update(userModel, userWrapper);
        if (update > 0) {
            QueryWrapper<UserModel> userModelQueryWrapperWrapper = new QueryWrapper<>();
            userModelQueryWrapperWrapper.select("id", "nickname", "description", "photo_link")
                    .eq("id", userModel.getId());
            return mbpUserMapper.selectOne(userModelQueryWrapperWrapper);
        }
        throw new MiniBoxException("用户信息更新失败");
    }

    /**
     * 更新密码，因为默认是使用验证码注册，所以只能先更新密码，再使用密码登陆
     *
     * @param id       用户id
     * @param password 新密码
     */
    @Transactional(rollbackFor = Throwable.class)
    public boolean updatePassword(Long id, String password) {
        String md5 = SecureUtil.md5(password);
        UpdateWrapper<UserModel> wrapper = new UpdateWrapper<UserModel>()
                .set("password", md5)
                .eq("id", id);
        return mbpUserMapper.update(null, wrapper) > 0;
    }

    /**
     * 用户列表
     *
     * @param userModel 实体
     * @return 用户列表
     */
    public List<UserModel> userList(UserModel userModel) {
        QueryWrapper<UserModel> wrapper = new QueryWrapper<>(userModel);
        return mbpUserMapper.selectList(wrapper);
    }

    /**
     * 查找用户以及每个用户的所有角色
     *
     * @param userModel 实例
     * @return 列表
     */
    public List<UserModel> userListWithRoles(UserModel userModel) {
        return mbpUserMapper.findUserAndEveryUserRoles(userModel);
    }

    /**
     * 赋予用户管理员角色
     *
     * @param userId 用户id
     * @return 用户列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public boolean addUserAdminRole(Long userId) {
        List<RoleModel> userRoles = mbpUserMapper.findUserRoles(userId);
        boolean anyMatch = userRoles.stream().anyMatch(roleModel -> roleModel.getName().equals(UserRole.ADMIN.getRole()));
        if (anyMatch) {
            throw new MiniBoxException("该用户已经是管理员");
        }
        return mbpUserMapper.setUserRoles(userId, UserRole.ADMIN.getRoleId()) > 0;
    }

    /**
     * 删除用户管理员角色
     *
     * @param userId 用户id
     * @return 用户列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public boolean deleteUserAdmin(Long userId) {
        return mbpUserMapper.deleteUserAdmin(userId) > 0;
    }

    /**
     * 删除用户以及所有相关内容
     *
     * @param id 用户id
     * @return 用户列表
     */
    @Transactional(rollbackFor = Throwable.class)
    public List<UserModel> deleteUser(Long id) {
        UserModel userModel = mbpUserMapper.selectById(id);
        if (userModel != null) {
            //删回复
            QueryWrapper<ReplyModel> roleWrapper = new QueryWrapper<>();
            roleWrapper.eq("user_id", id);
            mbpReplyMapper.delete(roleWrapper);
            //删评论
            QueryWrapper<CommentModel> commentWrapper = new QueryWrapper<>();
            commentWrapper.eq("user_id", id);
            mbpCommentMapper.delete(commentWrapper);
            //删帖子
            QueryWrapper<PostModel> postWrapper = new QueryWrapper<>();
            postWrapper.eq("author_id", id);
            mbpPostMapper.delete(postWrapper);
            //删订单
            QueryWrapper<OrderModel> orderWrapper = new QueryWrapper<>();
            orderWrapper.eq("user_id", id);
            mbpOrderMapper.delete(orderWrapper);
            //删角色
            mbpUserMapper.deleteUserRoles(id);
            int deleteById = mbpUserMapper.deleteById(id);
            if (deleteById > 0) {
                return userList(null);
            }
        }
        throw new MiniBoxException("删除失败");
    }
}
