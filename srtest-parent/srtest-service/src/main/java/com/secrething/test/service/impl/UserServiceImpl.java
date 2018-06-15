package com.secrething.test.service.impl;

import com.secrething.rpc.annotation.RPCProvidor;
import com.secrething.test.service.remote.UserSeriveRemote;

/**
 * Created by liuzz on 2018/6/11.
 */
@RPCProvidor("userService")
public class UserServiceImpl implements UserSeriveRemote {
    @Override
    public String save(String userName) {
        return "hello";
    }
}
