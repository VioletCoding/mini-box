package com.ghw.minibox.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.utils.RedisPrefix;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Violet
 * @description nimbus-jose-jwt是最受欢迎的JWT开源库，基于Apache 2.0开源协议，支持所有标准的签名(JWS)和加密(JWE)算法。
 * @date 2020/11/15
 */
@Component
@Slf4j
public class NimbusJoseJwt {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ObjectMapper objectMapper;

    private static final String SECRET = "This JWT Sign By VioletEverGarden,this is a SpringCloud web project";

    /**
     * 通过HMAC算法生成token
     * <p>
     * 该方法会先 创建JWS头，设置签名算法和类型
     * 然后 将负载信息封装到Payload
     * 然后 创建JWS对象
     * 然后 创建HMAC签名器
     * 最后 签名并序列化
     *
     * @param payloadParam 载荷
     * @return token
     * @throws JOSEException 抛出此异常时，生成token失败
     */
    public String generateTokenByHMAC(@NotNull PayloadDto payloadParam) throws JOSEException, JsonProcessingException {
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();

        ObjectMapper objectMapper = new ObjectMapper();
        String obj2json = objectMapper.writeValueAsString(payloadParam);

        Payload payload = new Payload(obj2json);

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        JWSSigner jwsSigner = new MACSigner(SECRET);
        jwsObject.sign(jwsSigner);
        String serialize = jwsObject.serialize();

        redisUtil.set(RedisPrefix.USER_TOKEN.getPrefix() + payloadParam.getUsername(), serialize);
        redisUtil.expire(RedisPrefix.USER_TOKEN.getPrefix() + payloadParam.getUsername(), payloadParam.getExp());
        return serialize;
    }

    /**
     * 定制jwt载荷
     *
     * @param username    用户名
     * @param exp         过期时间
     * @param authorities 权限
     * @return token
     */
    public PayloadDto buildToken(String username, Long exp, List<String> authorities) {
        return PayloadDto.builder()
                .username(username)
                .exp(exp)
                .iat(System.currentTimeMillis())
                .jti(IdUtil.fastSimpleUUID())
                .sub(IdUtil.fastSimpleUUID())
                .authorities(authorities)
                .build();
    }

    /**
     * 通过HMAC算法校验token
     * <p>
     * 该方法会先 从token中解析JWS对象
     * 然后 创建HMAC校验器
     *
     * @param token 颁发的token
     * @return 载荷对象
     * @throws ParseException 解析异常
     * @throws JOSEException  签名和加密异常
     */
    public PayloadDto verifyTokenByHMAC(String token) throws Exception {
        JWSObject jwsObject = JWSObject.parse(token);
        MACVerifier verifier = new MACVerifier(SECRET);
        if (!jwsObject.verify(verifier)) {
            throw new Exception("token签名不合法");
        }
        String payload = jwsObject.getPayload().toString();
        log.info("payload==>{}", payload);
        PayloadDto payloadDto = objectMapper.readValue(payload, PayloadDto.class);
        log.info("token的过期时间为==>{}", payloadDto.getExp());
        log.info("现在的时间为==>{}", new Date().getTime());
        if (new Date().getTime() - payloadDto.getExp() < 0) {
            throw new Exception("token已过期");
        }
        return payloadDto;
    }

    /**
     * 获取默认的PayloadDto方法，JWT过期时间为60分钟
     *
     * @return PayloadDto对象
     */
    public PayloadDto getPayloadDto() throws Exception {
        Date now = new Date();
        Date exp = DateUtil.offsetSecond(now, 60 * 60);
        return PayloadDto.builder()
                .sub("violet")
                .iat(now.getTime())
                .exp(exp.getTime())
                .jti(UUID.randomUUID().toString())
                .username("violet")
                .authorities(CollUtil.toList("ADMIN"))
                .build();
    }

}
