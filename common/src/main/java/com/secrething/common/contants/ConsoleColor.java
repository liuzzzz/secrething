package com.secrething.common.contants;

/**
 * Created by Idroton on 2018/9/22 4:17 PM.
 */
public enum ConsoleColor{

    BLACK(ConsoleColor.ANSI_BLACK),RED(ConsoleColor.ANSI_RED)
    ,GREEN(ConsoleColor.ANSI_GREEN),YELLOW(ConsoleColor.ANSI_YELLOW)
    ,BLUE(ConsoleColor.ANSI_BLUE),PURPLE(ConsoleColor.ANSI_PURPLE)
    ,GTAN(ConsoleColor.ANSI_CYAN),WHITE(ConsoleColor.ANSI_WHITE)
    ;
    private String color;
    ConsoleColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
}
