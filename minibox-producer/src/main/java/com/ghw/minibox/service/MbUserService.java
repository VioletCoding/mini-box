package com.ghw.minibox.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghw.minibox.entity.MbUser;
import com.nimbusds.jose.JOSEException;
import org.apache.commons.mail.EmailException;

/**
 * (MbUser)表服务接口
 *
 * @author Violet
 * @since 2020-11-19 12:20:22
 */
public interface MbUserService {


    /**
     * 通过username（邮箱）进行校验用户是否存在
     *
     * @param username 邮箱
     * @return 是否存在
     */
    boolean exist(String username) throws Exception;

    /**
     * 发送邮件
     *
     * @param username 邮箱
     * @param subject  邮件主题
     * @param msg      邮件内容
     * @param code     验证码
     * @throws EmailException 捕获此异常，抛出异常代表邮件发送失败
     */
    void sendEmail(String username, String subject, String msg, String code) throws EmailException;

    /**
     * 校验验证码是否正确
     *
     * @param key   K
     * @param value V
     * @return 校验是否通过
     */
    boolean authRegCode(String key, String value);


    /**
     * 登陆
     *
     * @param mbUser 用户实体
     * @return ReturnDto
     */
    String login(MbUser mbUser) throws JsonProcessingException, JOSEException;


    /**
     * 通过ID查询单条数据
     *
     * @param uid 主键
     * @return 实例对象
     */
    MbUser queryById(Long uid);


    /**
     * 新增数据
     *
     * @param mbUser 实例对象
     * @return 实例对象
     */
    MbUser insert(MbUser mbUser);

}