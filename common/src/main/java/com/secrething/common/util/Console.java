package com.secrething.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Idroton on 2018/9/8 4:21 PM.
 */
public class Console {
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void println(String pattern, Object... args) {
        Throwable t = new Throwable();
        StackTraceElement[] elements = t.getStackTrace();
        String dateLong;
        synchronized (format) {
            dateLong = format.format(new Date());
        }
        for (int i = 1; i < elements.length; i++) {
            StackTraceElement element = elements[i];
            if (!element.getClassName().endsWith(".Console")) {
                String s = MesgFormatter.format("{} PRINT [{}] {}:{} - {}", dateLong, Thread.currentThread().getName(), element.getClassName(), element.getLineNumber(), pattern);
                MesgFormatter.println(s, args);
            }
        }

    }

    public static void println(Object args) {
        println("{}", args);
    }
}
