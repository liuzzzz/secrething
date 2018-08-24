package com.secrething.rpc.core;

import com.secrething.common.util.SerializeUtil;
import com.secrething.rpc.protocol.MessageParser;
import com.secrething.rpc.protocol.MessageProtocol;
import io.netty.buffer.ByteBuf;

import java.util.List;

/**
 * Created by Idroton on 2018/8/12.
 */
public class ServerCodec implements Codec {
    @Override
    public void decode(ByteBuf buffer, List<Object> out) throws Exception {
        if (buffer.readableBytes() >= BASE_LENGTH) {
            MessageProtocol protocol = MessageParser.parse(buffer,BASE_LENGTH);
            if (null != protocol){
                RemoteRequest remoteRequest = SerializeUtil.deserialize(protocol.getContent(),RemoteRequest.class);
                out.add(remoteRequest);
            }
        }
    }
}
