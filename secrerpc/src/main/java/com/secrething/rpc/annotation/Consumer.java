package com.secrething.rpc.annotation;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liuzengzeng on 2017/12/17.
 * use it when need create one proxy to remote call provider's service
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Consumer {
    /**
     * service name or other same of Provider's value {@link Provider#value()}
     * @return
     */
    @Required
    String value();
}
