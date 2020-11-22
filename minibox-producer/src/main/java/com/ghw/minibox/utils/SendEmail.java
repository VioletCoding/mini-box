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

    private static final String authCode = "tieqxwykyjedbaif";

    /**
     * 发送邮件
     *
     * @param addTo   收件人的邮箱
     * @param subject 发送主题
     * @param msg     发送内容
     * @throws EmailException 邮箱异常，如果有异常抛出，则发送失败
     */
    public void createEmail(String addTo, String subject, String msg) throws EmailException {

        HtmlEmail email = new HtmlEmail();

        //SMTP服务器
        email.setHostName("smtp.qq.com");
        //设置发送的字符编码格式
        email.setCharset("utf-8");
        //设置收件人
        email.addTo(addTo);
        //发送人的邮箱和用户名，用户名可以随便填
        email.setFrom("1054197367@qq.com", "MiniboxOfficial");
        //授权码
        email.setAuthentication("1054197367@qq.com", authCode);
        //设置发送主题
        email.setSubject(subject);
        //设置发送信息
        email.setMsg(msg);
        //进行发送
        email.send();
    }

}
