package com.secrething.test.service.local;

import com.secrething.rpc.annotation.RPCConsumer;
import com.secrething.test.service.remote.UserSeriveRemote;
import org.springframework.stereotype.Component;

/**
 * Created by liuzz on 2018/6/11.
 */
@Component
public class UserServiceInvoker {

    @RPCConsumer("userService")
    private UserSeriveRemote userSeriveRemote;

    public void hello(){
        System.out.println(userSeriveRemote.save("hello"));
    }

}
