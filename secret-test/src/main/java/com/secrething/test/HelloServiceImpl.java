package com.secrething.test;

import com.secrething.common.util.MesgFormatter;

/**
 * Created by Idroton on 2018/8/17 10:54 PM.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void hello(String name) {
         MesgFormatter.println("{}: hello",name);
    }
}
