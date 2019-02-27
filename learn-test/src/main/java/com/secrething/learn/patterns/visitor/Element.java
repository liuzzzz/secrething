package com.secrething.learn.patterns.visitor;

/**
 * Created by liuzz on 2019-02-27 16:51.
 */
public interface Element {
    void accept(Visitor visitor);
}
