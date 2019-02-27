package com.secrething.learn.patterns.visitor;

/**
 * Created by liuzz on 2019-02-27 16:59.
 */
public class Main {
    public static void main(String[] args) {
        Home home = new Home();
        Visitor visitor = new HelloVisitor();
        home.accept(visitor);
    }
}
