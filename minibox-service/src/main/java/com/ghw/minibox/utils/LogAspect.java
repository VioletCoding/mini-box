package com.ghw.minibox.utils;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Violet
 * @description 日志切面
 * @date 2020/11/23
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * AOP切入点的集合
     */
    @Pointcut("@annotation(com.ghw.minibox.utils.AopLog)")
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
        printLog(joinPoint);
        return proceed;
    }

    /**
     * 异步打印日志
     * <p>
     * 该方法会打印标注了@AOPLog注解的方法的执行信息，包括方法在哪个类、入参是什么、执行时间
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
     */
    @Async
    public void printLog(ProceedingJoinPoint joinPoint) {
        long begin = System.currentTimeMillis();
        //方法签名，获取方法的所有信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取这个方法
        Method method = signature.getMethod();
        //获取方法名字
        String methodName = method.getName();
        log.info("开始执行{}方法", methodName);
        //自定义Bean，保存操作信息
        AopBean aopBean = new AopBean();
        //获取注解，AOPLog为自定义注解
        AopLog annotation = method.getAnnotation(AopLog.class);
        //获取默认值
        if (annotation != null) {
            aopBean.setOperation(annotation.value());
        }
        //获取类名
        String className = joinPoint.getTarget().getClass().getName();
        //将方法的全类名保存
        aopBean.setMethod(className + "." + methodName + "()");
        //获取连接点入参
        Object[] args = joinPoint.getArgs();
        //获取方法入参列表
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                params.append("  ").append(paramNames[i]).append(" : ").append(args[i]);
            }
            aopBean.setParam(params.toString());
        }
        long end = System.currentTimeMillis();
        aopBean.setTime(end - begin);
        log.info("结束执行{}方法，本次操作结果{}", methodName, aopBean);
    }
}
