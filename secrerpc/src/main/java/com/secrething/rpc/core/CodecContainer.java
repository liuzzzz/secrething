package com.secrething.rpc.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * Created by Idroton on 2018/8/12.
 */
public class CodecContainer {
    private final Codec codec;
    private final ChannelOutboundHandler encoder = new Encoder();
    private final ChannelInboundHandler decoder = new Decoder();

    public CodecContainer(Codec codec) {
        this.codec = codec;
    }

    public ChannelHandler getEncoder() {
        return encoder;
    }

    public ChannelHandler getDecoder() {
        return decoder;
    }

    private class Encoder extends MessageToByteEncoder {
        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
            codec.encode(msg, out);
        }
    }

    private class Decoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            codec.decode(in, out);
        }
    }
}
