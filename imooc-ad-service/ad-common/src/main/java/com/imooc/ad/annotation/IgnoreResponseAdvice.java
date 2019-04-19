package com.imooc.ad.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author
 * @description统一注解
 * @date 2019/4/18
 * 这个注解可以使用在 TYPE（Java类上面） 还可以使用在（method）方法上面
 * 我们把它标注在controller的方法或者类上的话 我们就可以不适用CommonResponse这个响应
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}
