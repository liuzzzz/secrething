package com.secrething.esutil.core;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by liuzz on 2018-11-27 12:06.
 */
public class RecordTest {

    @org.junit.Test
    public void create() {
        Message message = new Message();
        message.setContent("hello");
        message.setLastModifyTime(new Date());
        Record r = Record.create(message);
        System.out.println(r);

        Message message1 = MapWriter.parse(r.getSource(),Message.class);
        System.out.println(message1);
    }
}