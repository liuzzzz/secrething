package com.secrething.common.core;

import com.sun.tools.attach.VirtualMachine;
import org.junit.Test;

/**
 * Created by liuzz on 2018-12-18 14:16.
 */
public class AttachTest {


    @Test
    public void test(){
        System.out.println(VirtualMachine.list());
    }
}
