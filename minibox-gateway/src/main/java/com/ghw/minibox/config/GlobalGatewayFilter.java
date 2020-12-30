package com.ghw.minibox.config;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.utils.ResultCode;
import com.qiniu.util.Json;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author Violet
 * @description 全局过滤器
 * @date 2020/11/28
 */
@Component
@AllArgsConstructor
@Slf4j
public class GlobalGatewayFilter implements GlobalFilter, Ordered {

    @Resource
    private final NimbusJoseJwt jwt;
    @Resource
    private final RedisUtil redis;

    private final EnableAuth enableAuth;

    /**
     * 检查url是否在忽略列表里
     *
     * @param path 请求的路径
     * @return 是否被忽略
     */
    private boolean checkUrl(String path) {
        log.debug("checkUrl");
        return enableAuth.getIgnoreUrl()
                .stream()
                .map(url -> url.replace("/**", ""))
                .anyMatch(path::startsWith);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //如果未启用网关验证，则跳过
        if (!enableAuth.isEnableAuth()) {
            log.error("没有开启网关鉴权");
            return chain.filter(exchange);
        }
        //类似于/user/xxx这样的原始路径
        String path = exchange.getRequest().getURI().getRawPath();
        log.info("从exchange获取到的path=>{}", path);
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //如果在忽略的url里，则跳过
        if (checkUrl(path)) {
            log.info("请求的URL在忽略列表里，跳过");
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst("accessToken");
        //校验token
        if (StringUtils.isBlank(token)) {
            log.error("token为空");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return getVoidMono(response, new GenerateResult<>().fail(ResultCode.UNAUTHORIZED));
        }
        //获取载荷，如果抛异常，那就是token校验失败或者过期
        try {
            log.info("请求中的token=>{}", token);
            PayloadDto payloadDto = jwt.verifyTokenByHMAC(token);
            String username = payloadDto.getUsername();
            log.info("网关 - username=> {}", username);
            String tokenInRedis = redis.get(RedisUtil.TOKEN_PREFIX + username);
            log.info("tokenInRedis=>{}", tokenInRedis);
            if (!tokenInRedis.equals(token)) {
                log.info("token不合法！");
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return getVoidMono(response, new GenerateResult<>().fail(ResultCode.UNAUTHORIZED));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return getVoidMono(response, new GenerateResult<>().fail(ResultCode.UNAUTHORIZED));
        }
        //返回过滤链
        return chain.filter(exchange);
    }

    /**
     * 获取Mono，这个Mono是响应式编程里的内容
     *
     * @param serverHttpResponse 响应信息
     * @param r                  自定义返回
     * @return Mono
     */
    private Mono<Void> getVoidMono(ServerHttpResponse serverHttpResponse, ReturnDto<Object> r) {
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(Json.encode(r).getBytes(StandardCharsets.UTF_8));
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
