//package com.ghw.minibox.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Date;
//
///**
// * @author Violet
// * @description 全局过滤器
// * @date 2020/11/28
// */
//@Component
//@Slf4j
//public class GlobalGatewayFilter implements GlobalFilter, Ordered {
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        log.info("********come in MyLogGatewayFilter：" + new Date());
//        String accessToken = exchange.getRequest().getHeaders().getFirst("accessToken");
//        if (accessToken == null) {
//            log.info("*****accessToken,非法用户");
//            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
//            //必须setComplete()，不然配置不生效
//            return exchange.getResponse().setComplete();
//        }
//        //返回过滤链
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
