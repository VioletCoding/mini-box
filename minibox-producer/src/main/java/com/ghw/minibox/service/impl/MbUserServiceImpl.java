package com.ghw.minibox.service.impl;


import cn.hutool.crypto.digest.MD5;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.entity.MbUser;
import com.ghw.minibox.mapper.MbUserMapper;
import com.ghw.minibox.service.MbUserService;
import com.ghw.minibox.utils.DefaultUserInfoEnum;
import com.ghw.minibox.utils.SendEmail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
     * 通过username来搜索，如果返回的对象不为空，就证明邮箱username已经被占用
     *
     * @param username 邮箱
     * @return 实体
     */
    @Override
    public Boolean queryByUsername(String username) {
        log.info("进入queryByUsername()");
        MbUser mbUser = mbUserMapper.queryByUsername(username);
        log.info("结束queryByUsername()");
        return mbUser == null;
    }


    /**
     * 异步发送邮件，并写入Redis
     *
     * @param username 邮箱
     */
    @Async
    @Override
    public void sendEmail(String username) throws EmailException{
        log.info("进入sendEmail()");

        long start = System.currentTimeMillis();

        //如果queryByUsername()校验通过
        String subject = "Minibox验证码，请注意查收！";
        //生成一个6位数的简易验证码
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomNum = random.nextInt(10);
            sb.append(randomNum);
        }
        //发送
        sendEmail.createEmail(username, subject, "尊敬的迷你盒用户，您本次的验证码为：" + sb.toString() + " 本次验证码有效时间为5分钟！");
        log.info("本次邮件发送给==>{},主题为==>{},内容为==>{}", username, subject, sb.toString());

        //写进Redis
        redisUtil.set(username, sb.toString());
        //设置过期时间 300秒
        redisUtil.expire(username, 300L);
        log.info("本次存入Redis的Key==>{},Value==>{}", username, sb.toString());

        long end = System.currentTimeMillis();
        long cost = end - start;
        log.info("执行sendEmail()花费了==> {} 秒", TimeUnit.MILLISECONDS.toSeconds(cost));

        log.info("结束sendEmail()");
    }

    /**
     * 校验 验证码
     *
     * @param key   邮箱
     * @param value 验证码
     * @return true or false
     */
    @Override
    public boolean authRegCode(String key, String value) {
        log.info("进入authRegCode()");

        String valueFromRedis = redisUtil.get(key);
        log.info("从Redis获取到的value==>{}", valueFromRedis);

        log.info("结束authRegCode()");
        return value.equals(valueFromRedis);
    }

    /**
     * 校验后 注册
     *
     * @param mbUser 实体
     * @return true or false
     */
    @Override
    public boolean register(MbUser mbUser) {
        //默认用户名生成，MongoDB ID生成策略
        //生成一个带 "用户_" 前缀的昵称，后缀为随机值
        MbUser user = mbUser.setNickname(DefaultUserInfoEnum.NICKNAME.getMessage());
        log.info("生成的nickname为==>{}", user.getNickname());
        //使用MD5加密密码
        MD5 md5 = MD5.create();
        String digestHex16 = md5.digestHex16(mbUser.getPassword());
        mbUser.setPassword(digestHex16);
        //校验完毕后，就可以插入数据库了
        int result = mbUserMapper.insert(mbUser);
        //验证码用过了记得从redis移除
        redisUtil.remove(mbUser.getUsername());
        log.info("从Redis移除了key==>{}", mbUser.getUsername());
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