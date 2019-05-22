package com.secrething.common.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.util.Map;

/**
 * Created by liuzengzeng on 2017/12/10.
 */
public class SerializeUtil {
    private static final Map<Class,RuntimeSchema> schemaCache = new ConcurrentHashMap<>();

    private static RuntimeSchema getSchema(Class clzz){
        RuntimeSchema schema = schemaCache.get(clzz);
        if (null == schema){
            RuntimeSchema n = RuntimeSchema.createFrom(clzz);
            schemaCache.putIfAbsent(clzz,n);
            return n;
        }
        return schema;
    }
    public static byte[] serialize(Object t) {
        try {

            if (null == t) {
                throw new NullPointerException();
            }
            Class clzz =  t.getClass();
            RuntimeSchema schema = getSchema(clzz);
            byte[] bs = ProtostuffIOUtil.toByteArray(t, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            return bs;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T deserialize(byte[] bs, Class<T> clzz) {
        try {
            RuntimeSchema<T> schema = getSchema(clzz);
            T t = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(bs, t, schema);
            return t;
        } catch (Exception e) {
            return null;
        }
    }
}
