package com.secrething.service;

import com.secrething.common.util.MesgFormatter;
import com.secrething.common.util.Out;
import com.secrething.remote.HelloService;

/**
 * Created by Idroton on 2018/8/17 10:54 PM.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        Out.log("{}: hello", name);
         return MesgFormatter.format("{}: hello",name);
    }

    public static void main(String[] args) {
        System.out.println("9df759439ea30a925847a81ad07b076e655b3eb9".getBytes().length);
    }
}
