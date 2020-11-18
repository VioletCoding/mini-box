package com.ghw.minibox.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.ghw.minibox.dto.PayloadDto;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

/**
 * @author Violet
 * @description nimbus-jose-jwt是最受欢迎的JWT开源库，基于Apache 2.0开源协议，支持所有标准的签名(JWS)和加密(JWE)算法。
 * @date 2020/11/15
 */
@Component
@Slf4j
public class NimbusJoseJwt {

    private static final String SECRET = "Violet EverGarden";

    /**
     * 通过HMAC算法生成token
     *
     * @param payloadParam 载荷
     * @return token
     * @throws JOSEException 抛出此异常时，生成token失败
     */
    public String generateTokenByHMAC(String payloadParam) throws JOSEException {
        //创建JWS头，设置签名算法和类型
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();
        //将负载信息封装到Payload
        Payload payload = new Payload(payloadParam);
        //创建JWS对象
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        //创建HMAC签名器
        JWSSigner jwsSigner = new MACSigner(SECRET);
        //签名
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    /**
     * 通过HMAC算法校验token
     *
     * @param token 颁发的token
     * @return 载荷对象
     * @throws ParseException 解析异常
     * @throws JOSEException  签名和加密异常
     */
    public PayloadDto verifyTokenByHMAC(String token) throws Exception {
        //从token中解析JWS对象
        JWSObject jwsObject = JWSObject.parse(token);
        //创建HMAC校验器
        MACVerifier verifier = new MACVerifier(SECRET);

        if (!jwsObject.verify(verifier)) {
            throw new Exception("token签名不合法");
        }
        String payload = jwsObject.getPayload().toString();
        PayloadDto payloadDto = JSONUtil.toBean(payload, PayloadDto.class);
        if (payloadDto.getExp() < new Date().getTime()) {
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
