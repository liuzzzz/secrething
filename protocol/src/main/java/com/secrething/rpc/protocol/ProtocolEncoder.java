package com.secrething.rpc.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by liuzengzeng on 2017/12/11.
 * 协议编码器
 */
public class ProtocolEncoder extends MessageToByteEncoder<MessageProtocol>{
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getHead());
        out.writeLong(msg.getMessageUID());
        out.writeInt(msg.getContentLength());
        out.writeBytes(msg.getContent());
    }
}
