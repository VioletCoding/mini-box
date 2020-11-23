package com.ghw.minibox.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghw.minibox.component.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @author Violet
 * @description 日志切面
 * @date 2020/11/23
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private RedisUtil redisUtil;

    /**
     * AOP切入点的集合
     */
    @Pointcut("@annotation(com.ghw.minibox.utils.AOPLog)")
    public void pointcut() {
    }

    /**
     * 环绕
     * <p>
     * 打印
     * 方法执行时间、操作记录
     * <p>
     * 调用printLog方法，获取类名.方法名、入参，并写入到Redis
     *
     * @param joinPoint AOP切入点
     * @return 对象
     * @throws Throwable 异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        long beginTime = System.currentTimeMillis();
        long time = System.currentTimeMillis() - beginTime;
        printLog(joinPoint, time);
        return proceed;
    }

    /**
     * 异步打印日志
     * <p>
     * setOperation 注解上的描述
     * <p>
     * className 请求的类名
     * <p>
     * methodName 请求的方法名
     * <p>
     * args 请求的方法参数值
     * <p>
     * u 请求的方法参数名称
     *
     * @param joinPoint AOP切入点
     * @param time      时间
     */
    @Async
    public void printLog(ProceedingJoinPoint joinPoint, long time) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("进入到{}方法", joinPoint.getTarget().getClass().getName() + "." + signature.getName());
        AOPBean aopBean = new AOPBean();
        AOPLog annotation = method.getAnnotation(AOPLog.class);
        if (annotation != null) aopBean.setOperation(annotation.value());
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        aopBean.setMethod(className + "." + methodName + "()");
        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                params.append("  ").append(paramNames[i]).append(": ").append(args[i]);
            }
            aopBean.setParam(params.toString());
        }
        aopBean.setTime(TimeUnit.SECONDS.toSeconds(time));
        String json = objectMapper.writeValueAsString(aopBean);
        log.info("本次操作结果==>{}", json);
        redisUtil.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), json);
        log.info("已将操作记录写入到Redis");
        log.info("{}方法执行完毕", joinPoint.getTarget().getClass().getName() + "." + signature.getName());
    }
}
