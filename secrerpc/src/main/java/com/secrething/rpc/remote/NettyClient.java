package com.secrething.rpc.remote;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Idroton on 2018/8/11.
 */
public class NettyClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private Channel channel;
    private String host;
    private int port;

    public NettyClient() {
        this.port = 9999;
    }

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        start();
    }

    public void start() {
        //暂时只用 nio方式吧
        Bootstrap b = BootstrapFactory.newNioBootstrap();
        b.handler(new ClientInitializer(new ClientProcessService())).option(ChannelOption.SO_BACKLOG, Integer.valueOf(128)).option(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(true));
        ChannelFuture future = b.connect(host, port);
        boolean ret = future.awaitUninterruptibly(3000, TimeUnit.MILLISECONDS);
        if (ret && future.isSuccess()) {
            this.channel = future.channel();
        }
    }
}
