package com.secrething.rpc.remote.netty;

import com.secrething.rpc.core.RemoteResponse;
import com.secrething.rpc.core.ProcessService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Idroton on 2018/8/11.
 * message by client received may be response
 */
public class ClientSocketHandler extends SimpleChannelInboundHandler<RemoteResponse> {
    private final ProcessService processService;

    public ClientSocketHandler(ProcessService processService) {
        this.processService = processService;
    }

    public void channelRead0(ChannelHandlerContext ctx, RemoteResponse msg) throws Exception {
        processService.process(ctx.channel(), msg);
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
