package com.ghw.minibox.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.entity.MbUser;
import com.qiniu.common.QiniuException;
import org.apache.commons.mail.EmailException;

import java.io.InputStream;
import java.util.List;

/**
 * (MbUser)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 12:20:22
 */
public interface MbUserService {


    /**
     * 通过username来搜索
     *
     * @param username 邮箱
     * @return 实体
     */
    String queryByUsername(String username);

    /**
     * 注册之前的操作
     *
     * @param username 用户名
     */
    void sendEmail(String username) throws EmailException;

    /**
     * 校验验证码是否正确
     *
     * @param key   用户名
     * @param value key对应的值
     * @return true or false
     */
    boolean authRegCode(String key, String value);

    /**
     * 验证过后调用此方法进行注册
     *
     * @param mbUser 实体
     * @return true or false
     */
    boolean register(MbUser mbUser) throws JsonProcessingException;

    /**
     * 登陆
     *
     * @param user 用户实体
     * @return true or false
     */
    String login(MbUser user) throws JsonProcessingException;

    /**
     * 异步方法
     * 七牛云上传，需要指定accessKey和secretKey以及bucket还有输入流
     *
     * @param ak          公钥
     * @param sk          私钥
     * @param bucket      对象空间
     * @param inputStream 输入流
     * @return true or false
     * @throws QiniuException 七牛云异常
     */
    boolean upload(String ak, String sk, String bucket, InputStream inputStream) throws QiniuException;


    /**
     * 通过ID查询单条数据
     *
     * @param uid 主键
     * @return 实例对象
     */
    MbUser queryById(Long uid);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param mbUser 实例对象
     * @return 对象列表
     */
    List<MbUser> queryAll(MbUser mbUser);

    /**
     * 新增数据
     *
     * @param mbUser 实例对象
     * @return 实例对象
     */
    MbUser insert(MbUser mbUser);

    /**
     * 修改数据
     *
     * @param mbUser 实例对象
     * @return 实例对象
     */
    MbUser update(MbUser mbUser);

    /**
     * 通过主键删除数据
     *
     * @param uid 主键
     * @return 是否成功
     */
    boolean deleteById(Long uid);

}