package com.secrething.common.core;

import com.secrething.common.Message;
import org.junit.Test;

/**
 * Created by liuzengzeng on 2018-11-25.
 */
public class RecordTest {

    @Test
    public void testBuild(){
        Message m = new Message();
        m.setContent("hello");
        m.setName("secret");
        Record r = Record.build(m);
        System.out.println(r);
    }

}