package com.secrething.common.util;

import com.secrething.common.contants.ConsoleColor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Idroton on 2018/9/8 4:21 PM.
 */
public class Console {
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


    public static void log(String pattern, Object... args) {
        Throwable t = new Throwable();
        StackTraceElement[] elements = t.getStackTrace();
        String dateLong = lockFormat();
        for (int i = 1; i < elements.length; i++) {
            StackTraceElement element = elements[i];
            if (!element.getClassName().endsWith(".Console")) {
                String s = MesgFormatter.format("{} CONSOLE [{}] {}:{} - {}", dateLong, Thread.currentThread().getName(), element.getClassName(), element.getLineNumber(), pattern);
                MesgFormatter.println(s, args);
                break;
            }
        }
    }

    private static synchronized String lockFormat() {
        return format.format(new Date());
    }

    public static void print(Object args) {
        print(args, null);
    }

    public static void print(Object args, ConsoleColor color) {
        if (null == color) {
            MesgFormatter.print("{}", args);
        } else
            MesgFormatter.print("{}{}{}", color.getColor(), args, ConsoleColor.ANSI_RESET);
    }

    public static void log(Object args, ConsoleColor color) {
        if (null == color) {
            log("{}", args);
        } else
            log("{}{}{}", color.getColor(), args, ConsoleColor.ANSI_RESET);
    }

    public static void log(Object args) {
        log(args, null);
    }

}
