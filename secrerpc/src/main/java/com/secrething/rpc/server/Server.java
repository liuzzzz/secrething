package com.secrething.rpc.server;

import com.secrething.common.util.MD5Util;
import com.secrething.rpc.annotation.RPCProvidor;
import com.secrething.rpc.protocol.ProtocolDecoder;
import com.secrething.rpc.protocol.ProtocolEncoder;
import com.secrething.rpc.registry.Constant;
import com.secrething.rpc.registry.ServiceRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by liuzengzeng on 2017/12/20.
 * 服务提供Server
 */
public class Server implements ApplicationContextAware, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void submit(Runnable task) {
        Inner.pool.execute(task);
    }

    private ChannelFuture rootFuture;
    private int port;
    private ServiceRegistry serviceRegistry = new ServiceRegistry();

    private Map<String, Object> handlerMap = new HashMap<>();

    public Server() {
        String protStr = LoadProperties.getConfig("service.port");
        int p = 9999;
        if (StringUtils.isNotBlank(protStr))
            p = Integer.valueOf(LoadProperties.getConfig("service.port"));
        this.port = p;
    }

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    private static final class GroupInner {
        private static final ConcurrentHashMap<EventLoopGroup, Byte> workingGroup = new ConcurrentHashMap<>();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final CountDownLatch downLatch = new CountDownLatch(1);
        Inner.pool.execute(new Runnable() {
            @Override
            public void run() {
                logger.info("socket server starting...");
                EventLoopGroup bossGroup;// = new NioEventLoopGroup();
                EventLoopGroup workerGroup;// = new NioEventLoopGroup();
                Class clzz;
                if (Constant.IS_LINUX) {
                    bossGroup = new EpollEventLoopGroup();
                    workerGroup = new EpollEventLoopGroup();
                    clzz = EpollServerSocketChannel.class;
                } else {
                    bossGroup = new NioEventLoopGroup();
                    workerGroup = new NioEventLoopGroup();
                    clzz = NioServerSocketChannel.class;
                }
                try {
                    ServerBootstrap bootstrap = new ServerBootstrap();
                    bootstrap.group(bossGroup, workerGroup).channel(clzz)
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel channel) throws Exception {
                                    channel.pipeline()
                                            .addLast(new ProtocolEncoder())
                                            .addLast(new ProtocolDecoder())
                                            .addLast(new ServerSocketHandler(handlerMap));
                                }
                            })
                            .option(ChannelOption.SO_BACKLOG, 128)
                            .childOption(ChannelOption.SO_KEEPALIVE, true);
                    logger.info("server arress : 127.0.0.1:{}", port);
                    ChannelFuture future = bootstrap.bind(port).sync();
                    rootFuture = future;
                    if (serviceRegistry != null) {
                        serviceRegistry.register("127.0.0.1:" + port);
                    }
                    logger.info("server bind success...");
                    downLatch.countDown();
                    future.channel().closeFuture().sync();
                    logger.info("server is closed success");
                } catch (InterruptedException e) {
                    logger.info("server is interruted may be closing event");
                } catch (Exception e) {
                    logger.error("server error", e);
                } finally {
                    workerGroup.shutdownGracefully();
                    bossGroup.shutdownGracefully();
                }
            }
        });
        downLatch.await();
        logger.info("spring load success");
    }


    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RPCProvidor.class);
        if (!serviceBeanMap.isEmpty()) {
            for (Map.Entry<String, Object> e : serviceBeanMap.entrySet()) {
                Object serviceBean = e.getValue();
                String name = e.getKey();
                String beanName = e.getValue().getClass().getAnnotation(RPCProvidor.class).value();
                if (StringUtils.isBlank(beanName))
                    beanName = name;
                handlerMap.put(MD5Util.getOneInstance().md5Gen16B(beanName), serviceBean);
            }
        }
    }

    public void stop() {
        logger.info("closing server ...");
        Inner.pool.shutdownNow();
        if (null != rootFuture) {
            rootFuture.channel().close();
        }
    }

    private static final class Inner {
        private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(16, 16, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
    }
}
