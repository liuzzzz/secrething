package com.secrething.adrift.search.push;

import com.secrething.adrift.search.push.core.Constants;

/**
 * Created by liuzz on 2018-12-08 15:22.
 */
public class Bootstrap {

    public void start() {
        final PushServer server = new PushServer(Constants.DEFAULT_PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
        }));
        server.init();
        server.start();
    }
}
