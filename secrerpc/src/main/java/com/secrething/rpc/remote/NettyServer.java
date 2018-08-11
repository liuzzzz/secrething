package com.secrething.rpc.remote;

import com.secrething.rpc.protocol.ProcessService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Idroton on 2018/8/11.
 */
public abstract class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private int port;

    public NettyServer() {
        this.port = 9999;
    }

    public NettyServer(int port) {
        this.port = port;
    }


    public abstract NettyServer getInstance();

    public abstract ProcessService getProcessService();

    public void start() {
        //暂时只用 nio方式吧
        ServerBootstrap b = ServerBootstrapFactory.newNioServerBootstrap();
        try {
            b.childHandler(new ServerInitializer(getProcessService())).option(ChannelOption.SO_BACKLOG, Integer.valueOf(128)).childOption(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(true));
            ChannelFuture f = b.bind(this.port).sync();
            logger.info("server started !");
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("server start error", e);
        } finally {
            b.config().childGroup().shutdownGracefully();
            b.config().group().shutdownGracefully();
        }

    }
}
