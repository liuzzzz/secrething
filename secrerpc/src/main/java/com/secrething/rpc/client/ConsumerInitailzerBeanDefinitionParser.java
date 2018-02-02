package com.secrething.rpc.client;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author liuzengzeng
 * @create 2018/1/20
 */
public class ConsumerInitailzerBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition("com.secrething.rpc.annotation.RPCConsumerInitializer");
        parserContext.getRegistry().registerBeanDefinition("consumerInitializer", builder.getBeanDefinition());
        return null;
    }
}
