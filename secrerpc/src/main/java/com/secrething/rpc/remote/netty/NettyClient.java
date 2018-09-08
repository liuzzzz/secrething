package com.secrething.rpc.remote.netty;

import com.secrething.common.util.MesgFormatter;
import com.secrething.rpc.core.*;
import com.secrething.rpc.factory.BootstrapFactory;
import com.secrething.rpc.remote.RemoteHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Idroton on 2018/8/11.
 */
public class NettyClient implements RemoteHandler {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private Channel channel;
    private String host;
    private int port;
    private Bootstrap bootstrap;
    private static final AtomicLong SENDER_IDX = new AtomicLong();
    private static final String SENDER_PREFIX = "sender-pool-{}";
    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 50, 30, TimeUnit.SECONDS, new SynchronousQueue<>(), r -> {
        Thread t = new Thread(r, MesgFormatter.format(SENDER_PREFIX, SENDER_IDX.incrementAndGet()));
        return t;
    }, (r, executor) -> {
        if (r instanceof RemoteFuture){
            RemoteFuture<RemoteRequest,RemoteResponse> rf = (RemoteFuture) r;
            RemoteRequest request = rf.getNeedSend();
            logger.error("rejected request {}",request);
        }
    });
    public NettyClient(URL url) {
        this(url.getHost(), url.getPort());
    }

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        open();
        connect();
    }

    public void open() {
        bootstrap = BootstrapFactory.newNioBootstrap();
        bootstrap.handler(new ClientInitializer(new ClientProcessService()))
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT).option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

    }

    private void connect() {
        final Channel oldChannel = NettyClient.this.channel;
        if (null != oldChannel) {
            oldChannel.close();
        }
        ChannelFuture future = bootstrap.connect(host, port);
        boolean ret = future.awaitUninterruptibly(3000, TimeUnit.MILLISECONDS);
        if (ret && future.isSuccess()) {
            this.channel = future.channel();
        }
    }


    @Override
    public RemoteResponse send(RemoteRequest remoteRequest) throws ExecutionException, InterruptedException {
        RemoteFuture<RemoteRequest,RemoteResponse> future = doSend(remoteRequest);
        return future.get();
    }

    @Override
    public RemoteResponse send(RemoteRequest remoteRequest, long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        RemoteFuture<RemoteRequest,RemoteResponse> future = doSend(remoteRequest);
        return future.get(timeout, unit);
    }

    private RemoteFuture<RemoteRequest,RemoteResponse> doSend(RemoteRequest remoteRequest) {
        long begin = System.currentTimeMillis();
        RemoteFuture<RemoteRequest,RemoteResponse> future = new RemoteFuture<>(remoteRequest, channel);
        Channel channel = this.channel;
        if (null == channel || !channel.isActive()) {
            connect();
            logger.info("connect to server cost ={}",System.currentTimeMillis() - begin);
        }
        pool.execute(future);
        return future;
    }


}
