package com.ghw.minibox.utils;

import java.lang.annotation.*;

/**
 * @author Violet
 * @description AOP切面类
 * @date 2020/11/23
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited//表示一个类如果使用@Inherited注解标志的注解@AOPLog，该类的子类也会继承到这个注解
@Documented//可以生成文档
public @interface AOPLog {
    String value() default "";
}
