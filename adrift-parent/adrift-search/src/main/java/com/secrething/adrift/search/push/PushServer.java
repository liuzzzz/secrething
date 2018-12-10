package com.secrething.adrift.search.push;

import com.secrething.adrift.search.push.core.BaseServer;
import com.secrething.adrift.search.push.handler.AuthHandler;
import com.secrething.adrift.search.push.handler.ChannelHolder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuzz on 2018-12-08 15:33.
 */
public class PushServer extends BaseServer {
    private ScheduledExecutorService executorService;
    private static final CountDownLatch l = new CountDownLatch(1);
    public void await(){
        try {
            l.await();
        }catch (Exception e){

        }
    }
    public PushServer(int port) {
        this.port = port;
        executorService = Executors.newScheduledThreadPool(2);

        // 定时扫描所有的Channel，关闭失效的Channel
        executorService.scheduleAtFixedRate(() -> {
            logger.info("scanNotActiveChannel --------");
            ChannelHolder.scanNotActiveChannel();
        }, 3, 60, TimeUnit.SECONDS);

        // 定时向所有客户端发送Ping消息
        //executorService.scheduleAtFixedRate(() -> ChannelHolder.broadCastPing(), 3, 50, TimeUnit.SECONDS);
    }

    @Override
    public void start() {
        b.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(defLoopGroup,
                                new HttpServerCodec(),   //请求解码器
                                new HttpObjectAggregator(65536),//将多个消息转换成单一的消息对象
                                new ChunkedWriteHandler(),  //支持异步发送大的码流，一般用于发送文件流
                                new IdleStateHandler(60, 0, 0), //检测链路是否读空闲
                                new AuthHandler()//, //处理握手和认证
                                //new MessageHandler()    //处理消息的发送
                        );
                    }
                });

        try {
            cf = b.bind().sync();
            InetSocketAddress addr = (InetSocketAddress) cf.channel().localAddress();
            logger.info("WebSocketServer start success, port is:{}", addr.getPort());
            l.countDown();
            cf.channel().closeFuture().addListener((o) -> logger.info("WebSocketServer Close")).sync();
        } catch (InterruptedException e) {
            logger.error("WebSocketServer start fail,", e);
        }
    }

    @Override
    public void shutdown() {
        logger.warn("shutdown server");
        if (executorService != null) {
            executorService.shutdown();
        }
        super.shutdown();

    }
}
