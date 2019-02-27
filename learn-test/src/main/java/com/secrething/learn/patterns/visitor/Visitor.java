package com.secrething.learn.patterns.visitor;

/**
 * Created by liuzz on 2019-02-27 16:44.
 * 访问者
 */
public interface Visitor {

    void visit(Home home);

    void visit(Room room);

    void visit(Toilet toilet);

    void visit(Study study);


}
