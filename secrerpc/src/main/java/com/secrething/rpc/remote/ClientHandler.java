package com.secrething.rpc.remote;

import com.secrething.rpc.protocol.MessageProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Idroton on 2018/8/11.
 */
public class ClientHandler extends SimpleChannelInboundHandler<MessageProtocol>{
    private static final CopyOnWriteArrayList<Channel> CHANNELS = new CopyOnWriteArrayList<>();
    private static final AttributeKey<Integer> CHANNEL_ID = AttributeKey.valueOf("channelId");

    public void channelRead0(ChannelHandlerContext ctx, MessageProtocol mesg) throws Exception {
        /*if (mesg.getMessageType() == MessageProtocol.PROXY) {
            RemoteResponse response = SerializeUtil.deserialize(mesg.getContent(), RemoteResponse.class);
            receiveResponse(response);
        }*/
        //TODO
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadComplete();
    }
}
