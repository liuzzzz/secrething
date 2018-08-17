package com.secrething.rpc.remote;

import com.secrething.rpc.core.RemoteResponse;
import com.secrething.rpc.protocol.ProcessService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Idroton on 2018/8/11.
 */
public class ClientSocketHandler extends SimpleChannelInboundHandler<RemoteResponse> {
    private final ProcessService processService;

    public ClientSocketHandler(ProcessService processService) {
        this.processService = processService;
    }

    public void channelRead0(ChannelHandlerContext ctx, RemoteResponse msg) throws Exception {
        /*if (mesg.getMessageType() == MessageProtocol.PROXY) {
            RemoteResponse response = SerializeUtil.deserialize(mesg.getContent(), RemoteResponse.class);
            receiveResponse(response);
        }*/
        processService.process(msg);
        //
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
