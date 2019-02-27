package com.secrething.learn.patterns.visitor;

/**
 * Created by liuzz on 2019-02-27 16:44.
 * 家
 */
public class Home implements Element{
    private Room room = new Room();

    private Study study = new Study();

    private Toilet toilet = new Toilet();
    public void accept(Visitor visitor){
        visitor.visit(this);
        room.accept(visitor);
        study.accept(visitor);
        toilet.accept(visitor);
    }
}
