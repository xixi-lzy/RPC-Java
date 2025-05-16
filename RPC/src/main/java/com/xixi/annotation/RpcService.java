package com.xixi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcService {
    /**
     * 服务版本
     */
    String version() default "1.0";

    /**
     * 服务名
     */
    String value() default "";

    /**
     * 接口类
     */
    Class<?> interfaceClass();
}
