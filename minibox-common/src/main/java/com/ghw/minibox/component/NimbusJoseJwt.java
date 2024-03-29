package com.ghw.minibox.component;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.exception.MiniBoxException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

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
    private GenerateBean generateBean;
    private static final String SECRET = "This JWT Sign By VioletEverGarden,this is a SpringCloud Web Project";

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
     * @return token 返回token
     * @throws JOSEException 抛出此异常时，生成token失败
     */
    public String generateTokenByHMAC(PayloadDto payloadParam) throws JOSEException, JsonProcessingException {
        if (payloadParam == null) {
            throw new MiniBoxException("Payload is null");
        }
        JWSHeader jwsHeader = new JWSHeader
                .Builder(JWSAlgorithm.HS256)
                .type(JOSEObjectType.JWT)
                .build();
        ObjectMapper objectMapper = generateBean.getObjectMapper();
        String obj2json = objectMapper.writeValueAsString(payloadParam);
        Payload payload = new Payload(obj2json);
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        JWSSigner jwsSigner = new MACSigner(SECRET);
        jwsObject.sign(jwsSigner);
        String serialize = jwsObject.serialize();
        String key = RedisUtil.TOKEN_PREFIX + payloadParam.getUsername();
        redisUtil.set(key, serialize, payloadParam.getExp());
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
    public PayloadDto buildToken(String username,Long userId,Long exp, List<String> authorities) {
        return PayloadDto.builder()
                .username(username)
                .userId(userId)
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
            throw new MiniBoxException("token签名不合法");
        }
        String payload = jwsObject.getPayload().toString();
        ObjectMapper objectMapper = generateBean.getObjectMapper();
        return objectMapper.readValue(payload, PayloadDto.class);
    }
}
