package com.secrething.srpc.demo;

import com.secrething.rpc.server.LoadProperties;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by liuzz on 2018/6/16.
 */
public class Provider {
    public static void main(String[] args) {
        String xmlPath = LoadProperties.getConfig("xml.path");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        applicationContext.start();
    }

}
