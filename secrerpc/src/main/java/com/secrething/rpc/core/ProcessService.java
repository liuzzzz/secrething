package com.secrething.rpc.core;

import io.netty.channel.Channel;

/**
 * Created by Idroton on 2018/8/11.
 */
public interface ProcessService<R extends TransportData> {
    /**
     * process a input return a result
     *
     * @param inputMsg
     * @return S
     */
    RemoteFuture<R, ?> process(Channel channel, R inputMsg) throws Exception;
}
