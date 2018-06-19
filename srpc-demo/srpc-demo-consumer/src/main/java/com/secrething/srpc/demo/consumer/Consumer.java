package com.secrething.srpc.demo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by liuzz on 2018/6/16.
 */
public class Consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("srpc-demo-consumer.xml");
        context.start();
        HelloService helloService = context.getBean(HelloService.class);
        System.out.println(helloService.sayBye("consumer-2"));
        System.out.println(helloService.sayHello("consumer-1"));

    }
}
