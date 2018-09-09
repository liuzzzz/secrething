package com.secrething.common.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author liuzengzeng
 * @create 2018/1/20
 */
public class ParkUtil {
    /**
     * pause caller thread
     *
     * @return
     */
    public static boolean park() {
        LockSupport.park();
        return Thread.interrupted();
    }

    public static boolean park(long duration, TimeUnit unit) {
        LockSupport.parkNanos(unit.toNanos(duration));
        return Thread.interrupted();
    }

    /**
     * @param t
     */
    public static void unpark(Thread t) {
        LockSupport.unpark(t);
    }
}
