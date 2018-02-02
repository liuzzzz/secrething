package com.secrething.common.util;

import java.util.concurrent.locks.LockSupport;

/**
 * @author liuzengzeng
 * @create 2018/1/20
 */
public class ParkUtil {

    public static boolean park() {
        LockSupport.park();
        return Thread.interrupted();
    }
}
