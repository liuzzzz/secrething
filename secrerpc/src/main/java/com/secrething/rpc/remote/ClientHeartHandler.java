package com.secrething.rpc.remote;

import com.secrething.rpc.core.RemoteRequest;
import com.secrething.rpc.protocol.MessageProtocol;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by Idroton on 2018/8/11.
 */
public class ClientHeartHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                RemoteRequest heart = new RemoteRequest(RemoteRequest.HEART);
                ctx.channel().writeAndFlush(heart);
            }
        }
    }
}
