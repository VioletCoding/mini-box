package com.ghw.minibox.config;

import com.ghw.minibox.component.GenerateResult;
import com.ghw.minibox.component.NimbusJoseJwt;
import com.ghw.minibox.component.RedisUtil;
import com.ghw.minibox.dto.PayloadDto;
import com.ghw.minibox.dto.ReturnDto;
import com.ghw.minibox.utils.ResultCode;
import com.qiniu.util.Json;
import com.qiniu.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * @description 网关 全局过滤器
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
    @Resource
    private final EnableAuth enableAuth;
    @Resource
    private final GenerateResult<Object> gr;

    /**
     * 检查url是否在忽略列表里
     *
     * @param path 请求的路径
     * @return 是否被忽略（如果在忽略列表里，直接放行，不用鉴权）
     */
    private boolean checkUrl(String path) {
        return enableAuth.getIgnoreUrl()
                .stream()
                .map(url -> url.replace("/**", ""))
                .anyMatch(path::startsWith);
    }


    /**
     * 这个Mono涉及到响应式编程了，我也不太懂，姑且知道这么写
     *
     * @param exchange 交换（合同），用于得到请求和响应的信息，个人理解有点像中间件，可以在请求来的时候和响应的时候，中间插手做一些事情，比如鉴权
     * @param chain    过滤链
     * @return 如果存在于放行列表，直接return，如果未处于放行列表，那么鉴权，token有问题直接返回401，没问题直接放行请求SERVICE模块
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //如果未启用网关验证，则跳过
        if (!enableAuth.isEnableAuth()) {
            log.error("没有开启网关鉴权，请求将直接放行，是否配置错误？");
            return chain.filter(exchange);
        }

        //类似于/user/xxx这样的原始路径
        String path = exchange.getRequest().getURI().getRawPath();
        //如果在忽略的url里，则跳过
        if (checkUrl(path)) {
            log.info("请求的URL在忽略列表里，跳过 -> {}", path);
            return chain.filter(exchange);
        }

        log.info("需鉴权请求，path=>{}", path);
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String token = request.getHeaders().getFirst("accessToken");
        //校验token字符串
        if (StringUtils.isNullOrEmpty(token)) {
            log.error("token为空");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return getVoidMono(response, gr.fail(ResultCode.UNAUTHORIZED));
        }
        //获取载荷，如果抛异常，那就是token校验失败或者过期
        try {
            PayloadDto payloadDto = jwt.verifyTokenByHMAC(token);
            String username = payloadDto.getUsername();
            String tokenInRedis = redis.get(RedisUtil.TOKEN_PREFIX + username);

            if (StringUtils.isNullOrEmpty(tokenInRedis)) {
                log.error("token为空");
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return getVoidMono(response, gr.fail(ResultCode.UNAUTHORIZED));
            }
            if (!tokenInRedis.equals(token)) {
                log.info("token不合法！");
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return getVoidMono(response, gr.fail(ResultCode.UNAUTHORIZED));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return getVoidMono(response, gr.fail(ResultCode.UNAUTHORIZED));
        }
        //放行
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

    /**
     * 这个是过滤器的执行顺序，0是最先执行，值越大，执行顺序越靠后
     *
     * @return 执行顺序
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
