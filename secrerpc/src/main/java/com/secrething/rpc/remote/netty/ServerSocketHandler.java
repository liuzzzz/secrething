package com.secrething.rpc.remote.netty;

import com.secrething.rpc.core.RemoteRequest;
import com.secrething.rpc.core.ProcessService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Idroton on 2018/8/11.
 */
@Slf4j
@ChannelHandler.Sharable
public class ServerSocketHandler extends SimpleChannelInboundHandler<RemoteRequest> {
    private final ProcessService processService;

    public ServerSocketHandler(ProcessService processService) {
        this.processService = processService;
    }

    public void channelRead0(ChannelHandlerContext ctx, RemoteRequest inputMsg) throws Exception {

        log.info("server read type = {}", inputMsg.getType());
        if (RemoteRequest.HEART == inputMsg.getType()) {
            ctx.fireChannelRead(inputMsg);
        }
        if (RemoteRequest.PROXY == inputMsg.getType()) {
            processService.process(ctx.channel(), inputMsg);
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
