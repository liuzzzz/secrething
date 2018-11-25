package com.secrething.common.core;

import com.secrething.common.Message;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by liuzengzeng on 2018-11-25.
 */
public class RecordTest {

    @Test
    public void testBuild(){
        Message m = new Message();
        m.setContent("hello");
        m.setName("secret");
        Record r = Record.create(m);
        r.getSource().put("id",r.getId());


        Message mm = MapWriter.parse(null,Message.class).get();
        System.out.println(m == mm);
        ConcurrentMap<String,String> map = new ConcurrentHashMap<>();
        String s= map.putIfAbsent("h", "hello");
        String s1= map.computeIfAbsent("h",(h)-> "hello1");
        System.out.println(s);
        System.out.println(s1);
    }

}