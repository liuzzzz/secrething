package com.secrething.common;

import com.secrething.common.core.MapWriter;
import com.secrething.common.core.Record;
import org.junit.Test;

/**
 * Created by liuzengzeng on 2018-11-25.
 */
public class RecordTest {

    @Test
    public void testBuild() {
        Hello m = new Hello();
        m.setContent("hello");
        m.setName("secret");
        Record r = Record.create(m);
        r.getSource().put("id", r.getId());


        Hello mm = MapWriter.parse(r.getSource(), Hello.class);
        System.out.println(mm);
    }

}