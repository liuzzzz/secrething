package com.secrething.rpc.factory;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Idroton on 2018/8/11.
 */
public class BootstrapFactory {
    private BootstrapFactory() {
        throw new UnsupportedOperationException("instance not support");
    }

    private static final int childThreadsNum = Runtime.getRuntime().availableProcessors() << 1;
    private static AtomicInteger bossNameIdx = new AtomicInteger();
    private static AtomicInteger childNameIdx = new AtomicInteger();
    private static final ThreadFactory bossThreadFactory = (r) -> {
        Thread t = new Thread(r, String.format("BossThread-%d", bossNameIdx.getAndIncrement()));
        return t;
    };
    private static final ThreadFactory childThreadFactory = (r) -> {
        Thread t = new Thread(r, String.format("ChildThread-%d", childNameIdx.getAndIncrement()));
        return t;
    };

    public static ServerBootstrap newNioServerBootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        return b.group(new NioEventLoopGroup(1, bossThreadFactory), new NioEventLoopGroup(childThreadsNum, childThreadFactory)).channel(NioServerSocketChannel.class);
    }

    public static ServerBootstrap newEpollServerBootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        return b.group(new EpollEventLoopGroup(1, bossThreadFactory), new EpollEventLoopGroup(childThreadsNum, childThreadFactory)).channel(EpollServerSocketChannel.class);
    }

    public static Bootstrap newNioBootstrap() {
        Bootstrap b = new Bootstrap();
        return b.group(new NioEventLoopGroup(childThreadsNum, childThreadFactory)).channel(NioSocketChannel.class);
    }

    public static Bootstrap newEpollBootstrap() {
        Bootstrap b = new Bootstrap();
        return b.group(new EpollEventLoopGroup(childThreadsNum, childThreadFactory)).channel(EpollSocketChannel.class);
    }
}
