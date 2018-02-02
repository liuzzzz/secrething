package com.secrething.rpc.client;

import com.secrething.rpc.server.LoadProperties;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author liuzengzeng
 * @create 2018/1/20
 */
public class SrpcConsumerNameSpaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        if (!"consumer".equals(LoadProperties.getConfig("secrerpc.rule")))
            throw new IllegalArgumentException("rule not consumer");
        registerBeanDefinitionParser("consumer-load", new ConsumerInitailzerBeanDefinitionParser());
    }
}
