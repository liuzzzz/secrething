package com.secrething.rpc.annotation;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liuzengzeng on 2017/12/18.
 * used on provider's interfaces' target
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Provider {
    /**
     * maybe service name or other same of consumer's value {@link Consumer#value()}
     * @return
     * @see Consumer
     */
    @Required
    String value();
}
