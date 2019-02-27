package com.secrething.learn.patterns.visitor;

/**
 * Created by liuzz on 2019-02-27 16:45.
 * 书房
 */
public class Study implements Element{
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
