package com.secrething.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by liuzengzeng on 2017/12/21.
 * Server启动Spring容器
 */
public class ServerLoader {
    private static ClassPathXmlApplicationContext applicationContext = null;
    private static final Logger logger = LoggerFactory.getLogger(ServerLoader.class);
    private static Server server = null;

    public static void start() {
        final CountDownLatch latch = new CountDownLatch(1);
        Server.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    String xmlPath = LoadProperties.getConfig("xml.path");
                    applicationContext = new ClassPathXmlApplicationContext();
                    applicationContext.addBeanFactoryPostProcessor(new ServerRegistryPostProcessor());
                    applicationContext.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
                        @Override
                        public void onApplicationEvent(ApplicationEvent event) {
                            if (event instanceof ContextClosedEvent) {
                                logger.info("spring context closed");
                            }
                            if (event instanceof ContextStartedEvent) {
                                logger.info("spring context started");
                                server = applicationContext.getBean(Server.class);
                            }
                        }
                    });
                    applicationContext.registerShutdownHook();
                    applicationContext.setConfigLocation(xmlPath);
                    applicationContext.refresh();
                    applicationContext.start();

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("spring start failed");
                }

                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {

        }
    }

    public static void stop() {
        if (null != server) {
            server.stop();
        }

    }
}
