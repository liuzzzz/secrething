package com.secrething.learn.patterns.visitor;

/**
 * Created by liuzz on 2019-02-27 16:44.
 * 卧室
 */
public class Room implements Element{

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
