package com.secrething.consumer;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by liuzz on 2019-04-17 14:28.
 */
public class Main {
    /*static Jedis jedis = new Jedis("192.168.68.88",6379);
    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            LongAdder l = new LongAdder();
            @Override
            public void run() {

                while (true){
                    try {
                    jedis.publish("hello",String.valueOf(l.longValue()));

                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    l.increment();
                }
            }
        }).start();
        LockSupport.park();

    }*/

    public static void main(String[] args) throws Exception {
        new FastThreadLocalThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; ++i) {
                    FastThreadLocal ftl = new FastThreadLocal();
                    ftl.set("2");
                }
                FastThreadLocal ftl = new FastThreadLocal();
                Field f = null;
                try {
                    f = FastThreadLocal.class.getDeclaredField("index");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                f.setAccessible(true);
                ftl.set("2");
                ftl.get();
            }
        }).start();
        LockSupport.park();
    }
}
