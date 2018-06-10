package com.secrething.rpc.client;

import com.secrething.common.util.SerializeUtil;
import com.secrething.rpc.core.RemoteFuture;
import com.secrething.rpc.core.RemoteRequest;
import com.secrething.rpc.core.RemoteResponse;
import com.secrething.rpc.protocol.MessageProtocol;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuzengzeng on 2017/12/19.
 * 消费者netty的ChannelHandler
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private volatile Channel channel;
    private SocketAddress socketAddress;
    private ConcurrentHashMap<String, RemoteFuture> pendingCache = new ConcurrentHashMap<>();

    public SocketAddress getRemotePeer() {
        return socketAddress;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProtocol protocol = (MessageProtocol) msg;
        RemoteResponse response = SerializeUtil.deserialize(protocol.getContent(), RemoteResponse.class);
        RemoteFuture future = pendingCache.remove(response.getRequestId());
        logger.debug("received response from netty server");
        future.done(response);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.socketAddress = this.channel.remoteAddress();
    }

    public RemoteFuture sendRequest(final RemoteRequest remoteRequest) throws InterruptedException {
        final RemoteFuture future = new RemoteFuture(remoteRequest);
        logger.debug("netty client send request message begin");
        pendingCache.put(remoteRequest.getRequestId(), future);
        Client.submit(new Runnable() {
            @Override
            public void run() {

                byte[] bytes = SerializeUtil.serialize(remoteRequest);
                MessageProtocol protocol = new MessageProtocol(bytes.length, bytes);
                channel.writeAndFlush(protocol).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        logger.debug("netty client send request message end");
                    }
                });
            }
        });
        return future;
    }

    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

}
