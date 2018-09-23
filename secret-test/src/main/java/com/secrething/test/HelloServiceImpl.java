package com.secrething.test;

import com.secrething.common.util.MesgFormatter;
import com.secrething.common.util.Out;

/**
 * Created by Idroton on 2018/8/17 10:54 PM.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        Out.log("{}: hello", name);
         return MesgFormatter.format("{}: hello",name);
    }
}
