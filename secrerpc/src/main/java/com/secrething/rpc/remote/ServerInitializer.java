package com.secrething.rpc.remote;

import com.secrething.rpc.protocol.ProcessService;
import com.secrething.rpc.protocol.ProtocolDecoder;
import com.secrething.rpc.protocol.ProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by Idroton on 2018/8/11.
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private final ProcessService processService;
    private int readerIdleTimeSeconds = 5;
    private int writerIdleTimeSeconds = 10;
    private int allIdleTimeSeconds = 300;
    private int maxTimeoutTimes = 3;

    public ServerInitializer(ProcessService processService) {
        this.processService = processService;
    }

    public ServerInitializer readerIdleTimeSeconds(int readerIdleTimeSeconds) {
        this.readerIdleTimeSeconds = readerIdleTimeSeconds;
        return this;
    }

    public ServerInitializer writerIdleTimeSeconds(int writerIdleTimeSeconds) {
        this.writerIdleTimeSeconds = writerIdleTimeSeconds;
        return this;
    }

    public ServerInitializer allIdleTimeSeconds(int allIdleTimeSeconds) {
        this.allIdleTimeSeconds = allIdleTimeSeconds;
        return this;
    }

    public ServerInitializer maxTimeoutTimes(int maxTimeoutTimes) {
        this.maxTimeoutTimes = maxTimeoutTimes;
        return this;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
        ch.pipeline().addLast("messageEncoder", new ProtocolEncoder());
        ch.pipeline().addLast("messageDecoder", new ProtocolDecoder());
        ch.pipeline().addLast("readWriteAllListener", new ServerHeartHandler(maxTimeoutTimes));
        ch.pipeline().addLast("serverSocketHandler", new ServerSocketHandler(processService));
    }
}
