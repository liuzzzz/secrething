package com.secrething.rpc.factory;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Idroton on 2018/8/11.
 */
public class BootstrapFactory {
    private BootstrapFactory() {
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

    public static Bootstrap newNioBootstrap() {
        Bootstrap b = new Bootstrap();
        return b.group(new NioEventLoopGroup()).channel(NioSocketChannel.class);
    }

    public static Bootstrap newEpollBootstrap() {
        Bootstrap b = new Bootstrap();
        return b.group(new EpollEventLoopGroup()).channel(EpollSocketChannel.class);
    }
}
