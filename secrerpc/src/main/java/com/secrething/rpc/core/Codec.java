package com.secrething.rpc.core;

import com.secrething.rpc.protocol.MessageProtocol;
import com.secrething.rpc.protocol.MessageWriter;
import io.netty.buffer.ByteBuf;

import java.util.List;

/**
 * Created by Idroton on 2018/8/12.
 * encode/decode
 */
public interface Codec {
    int BASE_LENGTH = 4 + 4;

    /**
     * encode
     * @param obj
     * @param out
     * @throws Exception
     */
    default void encode(Object obj, ByteBuf out) throws Exception {
        MessageProtocol msg = new MessageProtocol(obj);
        MessageWriter.write(msg, out);
    }

    /**
     * decode
     * @param buffer
     * @param out
     * @throws Exception
     */
    void decode(ByteBuf buffer, List<Object> out) throws Exception;
}
