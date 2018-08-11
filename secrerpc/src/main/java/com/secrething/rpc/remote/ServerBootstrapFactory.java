package com.secrething.rpc.remote;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Idroton on 2018/8/11.
 */
public class ServerBootstrapFactory {
    private ServerBootstrapFactory() {
        throw new UnsupportedOperationException("instance not support");
    }

    public static ServerBootstrap newNioServerBootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        return b.group(new NioEventLoopGroup(), new NioEventLoopGroup()).channel(NioServerSocketChannel.class);
    }

    public static ServerBootstrap newEpollServerBootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        return b.group(new EpollEventLoopGroup(), new EpollEventLoopGroup()).channel(EpollServerSocketChannel.class);
    }
}
