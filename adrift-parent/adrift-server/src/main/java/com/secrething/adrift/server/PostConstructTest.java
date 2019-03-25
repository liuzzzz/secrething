package com.secrething.adrift.server;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by liuzz on 2019-03-20 09:59.
 */
@Component
public class PostConstructTest {

    @PostConstruct
    public void init(){
        System.out.println("PostConstructed");
    }
}
