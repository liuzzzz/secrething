package com.secrething.rpc.registry;

import com.secrething.rpc.core.URL;

/**
 * Created by Idroton on 2018/8/18 7:48 PM.
 */
public interface Registry {
    /**
     *
     */
    void register(URL url);

    void unregister(URL url);

}
