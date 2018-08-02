package com.printMethod.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: liteng
 * @Date: 2018/7/23 10:48
 * @Description: 注解中写方法的名字，答应日志时直接获取这个名字当做日志
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggerNameDescription {
    String methodNameDescription() default "没有被注解定义的方法";
}
