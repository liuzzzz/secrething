package com.secrething.rpc.protocol;

import io.netty.buffer.ByteBuf;

/**
 * Created by Idroton on 2018/8/12.
 */
public class MessageWriter {
    public static void write(MessageProtocol msg, ByteBuf out) {
        out.writeInt(msg.getHead());
        out.writeInt(msg.getContentLength());
        out.writeBytes(msg.getContent());
    }
}
