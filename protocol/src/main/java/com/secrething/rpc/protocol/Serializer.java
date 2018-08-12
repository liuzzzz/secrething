package com.secrething.rpc.protocol;

/**
 * Created by Idroton on 2018/8/12.
 */
public interface Serializer {
    <T> T decode(byte[] data, Class<T> clzz);

    byte[] encode(Object obj);
}
