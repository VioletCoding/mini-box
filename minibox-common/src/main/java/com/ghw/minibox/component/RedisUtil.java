package com.ghw.minibox.component;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Violet
 * @description Redis工具类
 * @date 2020/11/22
 */
@Component
public class RedisUtil {

    public static final String AUTH_PREFIX = "auth_code:";
    public static final String TOKEN_PREFIX = "auth_token:";
    public static final String LOGIN_FLAG = "login:";
    public static final String ORDER_PREFIX = "order:";
    public static final String REDIS_PREFIX = "minibox:";
    public static final String POST_PREFIX = "post:";
    public static final String GAME_PREFIX = "game:";
    public static final String BLOCK_PREFIX = "block:";
    public static final String USER_PREFIX = "user:";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * expire 单位是秒
     */
    public void set(String key, String value, long expire) {
        stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 失效时间
     */
    public void expire(String key, Long expire) throws NullPointerException {
        stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    public void remove(List<String> key) {
        stringRedisTemplate.delete(key);
    }

    public Long increment(String key, Long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    public Boolean exist(String key) {
        return stringRedisTemplate.hasKey(key);
    }

}
