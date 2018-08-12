package com.secrething.rpc.remote;

import com.secrething.rpc.core.RemoteRequest;
import com.secrething.rpc.protocol.ProcessService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Idroton on 2018/8/11.
 */
public class ServerSocketHandler extends SimpleChannelInboundHandler<RemoteRequest> {
    private final ProcessService processService;

    public ServerSocketHandler(ProcessService processService) {
        this.processService = processService;
    }

    public void channelRead0(ChannelHandlerContext ctx, RemoteRequest inputMsg) throws Exception {
        Object obj = processService.process(inputMsg);
        ctx.writeAndFlush(obj);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
