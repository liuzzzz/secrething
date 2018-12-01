package xyz.seecret.demo;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuzz on 2018-12-01 14:40.
 */
public abstract class AbstractTest {
    @Autowired
    TestHandler handler;

    public void hello(){
        System.out.println(handler);
    }
}
