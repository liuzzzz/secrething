package com.secrething.rpc.remote;

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
                String s = "not die";
                byte[] bytes = s.getBytes();
                MessageProtocol protocol = new MessageProtocol(bytes.length, bytes);
                protocol.setMessageUID(System.currentTimeMillis());
                protocol.setMessageType(MessageProtocol.HEART);
                ctx.channel().writeAndFlush(protocol);
            }
        }
    }
}
