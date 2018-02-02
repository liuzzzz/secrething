package com.secrething.rpc.client;

import com.secrething.rpc.protocol.ProtocolDecoder;
import com.secrething.rpc.protocol.ProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by liuzengzeng on 2017-12-20.
 * 服务消费端通信初始化
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline cp = socketChannel.pipeline();
        cp.addLast(new ProtocolEncoder());
        cp.addLast(new ProtocolDecoder());
        cp.addLast(new ClientHandler());
    }
}
