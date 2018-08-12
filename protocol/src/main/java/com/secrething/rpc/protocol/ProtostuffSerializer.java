package com.secrething.rpc.protocol;

import com.secrething.common.util.SerializeUtil;

/**
 * Created by Idroton on 2018/8/12.
 */
public class ProtostuffSerializer implements Serializer {
    private ProtostuffSerializer() {
        if (null != Inner.SERIALIZER)
            throw new UnsupportedOperationException();
    }

    public static ProtostuffSerializer getInstance() {
        return Inner.SERIALIZER;
    }

    @Override
    public <T> T decode(byte[] data, Class<T> clzz) {
        return SerializeUtil.deserialize(data, clzz);
    }

    @Override
    public byte[] encode(Object obj) {
        return SerializeUtil.serialize(obj);
    }

    private static class Inner {
        private static final ProtostuffSerializer SERIALIZER = new ProtostuffSerializer();
    }
}
