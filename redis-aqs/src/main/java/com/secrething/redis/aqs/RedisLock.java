package com.secrething.redis.aqs;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by liuzz on 2019-05-17 17:24.
 */
public class RedisLock {

    private static final int FREE = 0;
    private static final int LOCKED = 1;
    private static final String SET_IF_NOT_EXITS = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final String SUCCESS = "OK";
    private static final String LOCK_SUBJECT_KEY = "lock_subject";
    private static final String WAIT_QUEUE = "wait_queue";
    private static final ConcurrentMap<String, MarkObject> cache = new ConcurrentHashMap<>();
    private static AtomicInteger wakeupThreadCounter = new AtomicInteger();
    private static final ScheduledExecutorService wakeupExecutor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            String wakeupThreadNamePattern = "Wakeup-Thread-%d";
            return new Thread(r, String.format(wakeupThreadNamePattern, wakeupThreadCounter.getAndIncrement()));
        }
    });
    private final String key;
    private final String lockInfo = UUID.randomUUID().toString();
    private volatile int state;
    private Jedis jedis;
    private Jedis subJedis;
    private volatile boolean inWaitQueue = false;
    private volatile boolean shutdown = false;
    private String host;
    private int port;

    public RedisLock(Properties redisConf, String key) {
        checkConf(redisConf);
        this.jedis = new Jedis(host, port);
        this.subJedis = new Jedis(host, port);
        String pass = redisConf.getProperty("auth");
        if (null != pass) {
            jedis.auth(pass);
            subJedis.auth(pass);
        }
        this.key = key;
        //
        Runnable listener = new Runnable() {
            @Override
            public void run() {
                while (!shutdown) {
                    sub();
                }

            }
            private void sub() {
                try {
                    RedisLock.this.subJedis.subscribe(new JedisPubSub() {
                        @Override
                        public void onMessage(String channel, String message) {
                            System.out.println(String.format("one wait node waked lockInfo=%s", message));
                            MarkObject mk = cache.get(message);
                            if (null != mk)
                                LockSupport.unpark(mk.parkedThread);

                        }
                    }, LOCK_SUBJECT_KEY);
                } catch (Exception e) {
                    //
                }
            }
        };
        new Thread(listener, "RedisLockListener").start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            RedisLock.this.shutdown = true;
            System.out.println("process killed");
        }));
    }

    private static long now() {
        return System.nanoTime();
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("host", "192.168.68.88");
        properties.setProperty("port", "6379");
        /*Jedis j = new Jedis("192.168.68.88",6379);
        String s = j.get("hello");
        System.out.println(s);*/

        RedisLock lock = new RedisLock(properties, "hello");
        try {
            lock.lock(20);
            Thread.sleep(10000);
            lock.lock(20);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.exit(0);
    }

    private void checkConf(Properties redisConf) {
        if (null == redisConf)
            throw new IllegalArgumentException("redisConf must not be null");
        String host = redisConf.getProperty("host");
        if (null == host)
            throw new IllegalArgumentException("host must not be null");
        this.host = host;
        String port = redisConf.getProperty("port");
        if (null == port)
            throw new IllegalArgumentException("port must not be null");
        this.port = Integer.valueOf(port);


    }

    public void lock(int timeoutSeconds) throws TimeoutException {
        boolean lockedSuccess = tryLock(timeoutSeconds);
        if (FREE == this.state && lockedSuccess) {
            this.state = LOCKED;
            System.out.println(String.format("locked success key=%s lockInfo=%s state=%d", key, lockInfo, this.state));
        } else {
            System.out.println(String.format("locked failed key=%s lockInfo=%s  state=%d tryLockResult=%s", key, lockInfo, this.state, String.valueOf(lockedSuccess)));
            MarkObject mk = new MarkObject(timeoutSeconds);
            long begin = now();
            long delay = now() - begin;
            if (delay < TimeUnit.SECONDS.toNanos(timeoutSeconds) && !tryLock(timeoutSeconds)) {
                System.out.println("获取失败 准备睡眠");
                synchronized (this) {
                    if (!inWaitQueue) {
                        jedis.lpush(WAIT_QUEUE, lockInfo);
                        inWaitQueue = true;
                        wakeupExecutor.schedule(mk, timeoutSeconds, TimeUnit.SECONDS);
                        cache.putIfAbsent(lockInfo, mk);

                    }
                }
                while ((now() - begin) < TimeUnit.SECONDS.toNanos(timeoutSeconds) && !tryLock(timeoutSeconds))
                    LockSupport.parkNanos(now() - begin);
                System.out.println("我被叫醒");
                Thread.interrupted();
                if (FREE == this.state && tryLock(timeoutSeconds)) {
                    System.out.println("被叫醒之后,锁成功了");
                    this.state = LOCKED;
                    return;
                }

            }
            throw new TimeoutException(String.format("get lock failed key=%s lockInfo=%s", key, lockInfo));

        }


    }

    private boolean tryLock(int timeoutSeconds) {
        if (lockInfo.equals(jedis.get(key))) {
            jedis.expire(key, timeoutSeconds);
            return true;
        }
        return SUCCESS.equals(jedis.set(key, lockInfo, SET_IF_NOT_EXITS, SET_WITH_EXPIRE_TIME, timeoutSeconds));
    }

    private void wakeupNext() {
        String waitInfo = jedis.lpop(WAIT_QUEUE);
        if (null != waitInfo) {
            System.out.println(String.format("%s next wakeuped", waitInfo));
            jedis.publish(LOCK_SUBJECT_KEY, waitInfo);
        } else {
            System.out.println("没有后驱节点了");
        }
    }

    public void unlock() {
        System.out.println(String.format("%s release", key));
        if (lockInfo.equals(jedis.get(key))) {
            System.out.println("我要释放" + lockInfo);
            jedis.del(key);
            wakeupNext();
            this.state = FREE;
            cache.remove(lockInfo);
        }

    }

    private class MarkObject implements Runnable {
        int expire;
        Thread parkedThread = Thread.currentThread();

        public MarkObject(int expire) {
            this.expire = expire;
        }


        @Override
        public void run() {
            try {
                LockSupport.unpark(parkedThread);
                while (null == jedis.get(RedisLock.this.key) && jedis.llen(RedisLock.this.key) > 0) {
                    wakeupNext();
                }
            } catch (Exception e) {
            }

        }
    }

}
