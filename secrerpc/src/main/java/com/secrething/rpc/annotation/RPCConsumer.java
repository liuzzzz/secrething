package com.secrething.rpc.annotation;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liuzengzeng on 2017/12/17.
 * 给消费者端给remote接口添加注解
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RPCConsumer {
    @Required
    String value();
}
