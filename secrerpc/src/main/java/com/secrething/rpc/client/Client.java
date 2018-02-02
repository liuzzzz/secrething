package com.secrething.rpc.client;

import com.secrething.rpc.registry.ServiceConnectManage;
import com.secrething.rpc.registry.ServiceDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuzengzeng on 2017/12/19.
 * 服务消费者Client
 */
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private static ServiceDiscovery serviceDiscovery;
    private static boolean running = false;

    static boolean isRunning() {
        return running;
    }

    public static void close() {
        if (isRunning()) {
            Inner.threadPoolExecutor.shutdown();
            serviceDiscovery.close();
            ServiceConnectManage.getInstance().stop();
            running = false;
            logger.info("client stoped");
        }
    }

    public static void init() {
        if (isRunning())
            return;
        final CountDownLatch latch = new CountDownLatch(1);
        serviceDiscovery = new ServiceDiscovery(latch);
        submit(new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await();
                    running = true;
                    logger.info("client init completed");
                } catch (InterruptedException e) {

                }

            }
        });
    }

    public static void submit(Runnable task) {
        Inner.threadPoolExecutor.submit(task);
    }

    //延迟加载
    private static final class Inner {
        private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5000));

    }
}
