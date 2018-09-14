package com.secrething.rpc.remote.netty;

import com.secrething.rpc.core.ClientCodec;
import com.secrething.rpc.core.CodecContainer;
import com.secrething.rpc.core.ProcessService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by Idroton on 2018/8/11.
 *
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    private final ProcessService processService;
    private int readerIdleTimeSeconds = 5;
    private int writerIdleTimeSeconds = 10;
    private int allIdleTimeSeconds = 300;

    public ClientInitializer(ProcessService processService) {
        this.processService = processService;
    }

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
        CodecContainer codecContainer = new CodecContainer(new ClientCodec());
        ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
        ch.pipeline().addLast("messageEncoder", codecContainer.getEncoder());
        ch.pipeline().addLast("messageDecoder", codecContainer.getDecoder());
        ch.pipeline().addLast("clientHeartHandler", new ClientHeartHandler());
        ch.pipeline().addLast("clientHandler", new ClientSocketHandler(processService));
    }
}
