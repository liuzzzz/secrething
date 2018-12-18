package com.secrething.common.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.tools.attach.VirtualMachine;
import org.junit.Test;
import sun.jvm.hotspot.tools.Tool;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by liuzz on 2018-12-18 14:17.
 */
public class MesgFormatterTest {

    @Test
    public void test(){
        VirtualMachine vm = null;
        try{
            vm = VirtualMachine.attach("1913");
            System.out.println(JSONObject.toJSONString(vm.getSystemProperties()));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(null != vm){
                try {
                    vm.detach();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }





    }

    public static class TestTool extends Tool{

        @Override
        public void run() {

        }
    }

}