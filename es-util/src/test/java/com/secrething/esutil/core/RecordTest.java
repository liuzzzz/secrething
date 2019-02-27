package com.secrething.esutil.core;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by liuzz on 2018-11-27 12:06.
 */
public class RecordTest {

    @org.junit.Test
    public void create() throws Exception {
       /* Message message = new Message();
        message.setContent("hello");
        message.setLastModifyTime(new Date());
        Record r = Record.create(message,DatePaserEnum.STRING);
        System.out.println(r);

        Message message1 = MapWriter.parse(r.getSource(),Message.class,DatePaserEnum.LONG);
        System.out.println(message1);*/
        //Field f = Message.class.getDeclaredField("list1");
        //System.out.println(Arrays.toString(f.getGenericType().getTypeName().split("<")));
        //System.out.println(f.getGenericType().getTypeName());
        System.out.println(Arrays.toString(ArrayList.class.getInterfaces()));
    }
}