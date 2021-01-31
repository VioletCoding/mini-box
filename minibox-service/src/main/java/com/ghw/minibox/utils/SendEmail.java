package com.ghw.minibox.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Component;

/**
 * @author Violet
 * @description 发送邮箱验证码工具类
 * @date 2020/11/22
 */
@Component
public class SendEmail {
    private static final String AUTH_CODE = "tieqxwykyjedbaif";
    private static final String AUTHENTICATION = "1054197367@qq.com";
    private static final String FROM = "MiniboxOfficial";
    public static final String SUBJECT = "Minibox验证码，请注意查收！";
    public static final String LOGIN_MESSAGE = "您正在登陆迷你盒，本次验证码5分钟内有效：";
    public static final String REGISTER_MESSAGE = "您正在注册迷你盒，本次验证码5分钟内有效：";
    public static final String OTHER_MESSAGE = "迷你盒验证码，本次验证码5分钟内有效：";

    /**
     * 发送邮件
     * <p>
     * HtmlEmail 简单的邮件对象
     * <p>
     * setHostName SMTP服务器
     * <p>
     * setCharset 设置发送的字符编码格式
     * <p>
     * addTo 设置收件人
     * <p>
     * setFrom 发送人的邮箱和用户名，用户名可以随便填
     * <p>
     * setAuthentication 授权码
     * <p>
     * setSubject 设置发送主题
     * <p>
     * setMsg 设置发送信息
     * <p>
     * send 进行发送
     *
     * @param addTo   收件人的邮箱
     * @param subject 发送主题
     * @param message     发送内容
     * @throws EmailException 邮箱异常，如果有异常抛出，则发送失败
     */
    public void createEmail(String addTo, String subject, String message) throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.qq.com");
        email.setCharset("utf-8");
        email.addTo(addTo);
        email.setFrom("1054197367@qq.com", "MiniboxOfficial");
        email.setAuthentication("1054197367@qq.com", AUTH_CODE);
        email.setSubject(subject);
        email.setMsg(message);
        email.send();
    }

}
