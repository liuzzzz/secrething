package com.secrething.rpc.remote;

import com.secrething.rpc.protocol.ProtocolDecoder;
import com.secrething.rpc.protocol.ProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by Idroton on 2018/8/11.
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    private int readerIdleTimeSeconds = 5;
    private int writerIdleTimeSeconds = 10;
    private int allIdleTimeSeconds = 300;

    public ClientInitializer readerIdleTimeSeconds(int readerIdleTimeSeconds) {
        this.readerIdleTimeSeconds = readerIdleTimeSeconds;
        return this;
    }

    public ClientInitializer writerIdleTimeSeconds(int writerIdleTimeSeconds) {
        this.writerIdleTimeSeconds = writerIdleTimeSeconds;
        return this;
    }

    public ClientInitializer allIdleTimeSeconds(int allIdleTimeSeconds) {
        this.allIdleTimeSeconds = allIdleTimeSeconds;
        return this;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
        ch.pipeline().addLast("messageEncoder", new ProtocolEncoder());
        ch.pipeline().addLast("messageDecoder", new ProtocolDecoder());
        ch.pipeline().addLast("clientHeartHandler", new ClientHeartHandler());
        ch.pipeline().addLast("clientHandler", new ClientHandler());
    }
}
