package com.ghw.minibox.component;

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

    public void set(String key, String value) {
        srt.opsForValue().set(key, value);
    }

    public String get(String key) {
        return srt.opsForValue().get(key);
    }

    /**
     * 失效时间
     *
     * @param key    redis的key
     * @param expire 过期时间，单位是秒
     * @throws NullPointerException 空指针异常
     */
    public void expire(String key, Long expire) throws NullPointerException {
        if (key == null || key.equals("")) {
            return;
        }
        if (expire == null) {
            return;
        }
        srt.expire(key, expire, TimeUnit.SECONDS);
    }

    public void remove(String key) {
        srt.delete(key);
    }

    public Long increment(String key, Long delta) {
        return srt.opsForValue().increment(key, delta);
    }

    public Boolean exist(String key) {
        return srt.hasKey(key);
    }

}
