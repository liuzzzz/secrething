package com.secrething.rpc.remote;

import com.secrething.rpc.protocol.MessageProtocol;
import com.secrething.rpc.protocol.ProcessService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Idroton on 2018/8/11.
 */
public class ServerSocketHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private final ProcessService processService;

    public ServerSocketHandler(ProcessService processService) {
        this.processService = processService;
    }

    public void channelRead0(ChannelHandlerContext ctx, MessageProtocol inputMsg) throws Exception {
        MessageProtocol outMsg = processService.process(inputMsg);
        ctx.writeAndFlush(outMsg);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
