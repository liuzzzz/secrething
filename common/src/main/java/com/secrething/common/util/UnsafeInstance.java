package com.secrething.common.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author liuzengzeng
 * @create 2018/1/20
 */
public class UnsafeInstance {
    private static final Unsafe INSTANCE;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            INSTANCE = (Unsafe) field.get(null);
        } catch (NoSuchFieldException e) {
            throw new Error();
        } catch (IllegalAccessException e) {
            throw new Error();
        }
    }

    public static Unsafe getUnsafe() {
        return INSTANCE;
    }
}
