package com.ghw.minibox.component;

import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Violet
 * @description Redis工具类
 * @date 2020/11/22
 */
@Component
public class RedisUtil {

    @Resource
    private StringRedisTemplate srt;

    /**
     * 切换Redis数据库
     *
     * @param dbNum 数据库索引 0-16
     */
    public synchronized boolean setDB(int dbNum) {
        //只允许输入0-16的数
        if (dbNum >= 0 && dbNum <= 16) {
            //获取连接
            LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) srt.getConnectionFactory();
            //如果输入的库索引已经是现在连接的库，不允许切换
            if (connectionFactory != null && dbNum != connectionFactory.getDatabase()) {
                connectionFactory.setDatabase(dbNum);
                this.srt.setConnectionFactory(connectionFactory);
                connectionFactory.resetConnection();
                return true;
            }
        }
        return false;
    }

    public void set(String key, String value) {
        srt.opsForValue().set(key, value);
    }

    public String get(String key) {
        return srt.opsForValue().get(key);
    }

    public void expire(String key, Long expire) throws NullPointerException {
        if (key == null || key.equals("")) return;
        if (expire == null) return;
        srt.expire(key, expire, TimeUnit.SECONDS);
    }

    public void remove(String key) {
        srt.delete(key);
    }

    public Long increment(String key, Long delta) {
        return srt.opsForValue().increment(key, delta);
    }

}
