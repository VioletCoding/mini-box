package com.ghw.minibox.config;

import com.ghw.minibox.component.GenerateResult;
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
        //TODO
        if (StringUtils.isBlank(token)) {
            log.error("token为空");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return getVoidMono(response, new GenerateResult<>()
                    .custom(ResultCode.UNAUTHORIZED
                            .getCode(), ResultCode.UNAUTHORIZED
                            .getMessage()));
        }
        //返回过滤链
        return chain.filter(exchange);
    }


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
