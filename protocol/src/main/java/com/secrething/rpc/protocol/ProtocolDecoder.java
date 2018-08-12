package com.secrething.rpc.protocol;

import com.secrething.common.contants.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by liuzengzeng on 2017/12/11.
 * 协议解码器
 */
public class ProtocolDecoder extends ByteToMessageDecoder {
    //头 消息id 内容长度
    private final int BASE_LENGTH = 4 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        // 可读长度必须大于基本长度
        if (buffer.readableBytes() >= BASE_LENGTH) {
            MessageProtocol protocol = MessageParser.parse(buffer, BASE_LENGTH);
            if (null == protocol)
                return;
            out.add(protocol);
        }
    }
}
