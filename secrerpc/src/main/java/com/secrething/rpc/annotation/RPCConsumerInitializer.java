package com.secrething.rpc.annotation;

import com.secrething.common.util.MD5Util;
import com.secrething.rpc.client.Client;
import com.secrething.rpc.factory.RemoteServiceFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.lang.reflect.Field;

/**
 * Created by liuzengzeng on 2017/12/22.
 * 注解发现,并注入remote接口的代理实例
 */
public class RPCConsumerInitializer implements BeanPostProcessor, InitializingBean, ApplicationListener<ContextClosedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(RPCConsumerInitializer.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(RPCConsumer.class)) {
                    continue;
                }
                Class clzz = field.getType();
                field.setAccessible(true);
                String fieldBeanName = field.getAnnotation(RPCConsumer.class).value();
                if (StringUtils.isBlank(fieldBeanName)) {
                    fieldBeanName = field.getName();
                }
                try {
                    field.set(bean, RemoteServiceFactory.getJDKProxyInstance(clzz, fieldBeanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Client.init();
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        Client.close();
    }
}
