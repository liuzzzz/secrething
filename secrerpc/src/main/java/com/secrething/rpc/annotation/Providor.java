package com.secrething.rpc.annotation;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liuzengzeng on 2017/12/18.
 * 服务提供者端给remote接口的具体实现类添加注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Providor {
    @Required
    String value();
}
