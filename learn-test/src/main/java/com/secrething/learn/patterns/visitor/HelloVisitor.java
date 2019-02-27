package com.secrething.learn.patterns.visitor;

/**
 * Created by liuzz on 2019-02-27 16:47.
 */
public class HelloVisitor implements Visitor {

    @Override
    public void visit(Home home) {
        System.out.println("into home");
    }

    @Override
    public void visit(Room room) {
        System.out.println("into room");
    }

    @Override
    public void visit(Toilet toilet) {
        System.out.println("into toilet");
    }

    @Override
    public void visit(Study study) {
        System.out.println("into study");
    }
}
