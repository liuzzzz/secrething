package com.secrething.rpc.core;

import lombok.Data;

/**
 * Created by liuzengzeng on 2017/12/18.
 * 通信请求封装
 */
@Data
public class RemoteRequest implements TransportData {
    public static final int PROXY = 0;
    public static final int HEART = 1;
    private final int type;
    private String id;
    private String beanName;
    private String clzzName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
    public RemoteRequest(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RemoteRequest{" +
                "type=" + type +
                ", id='" + id + '\'' +
                ", beanName='" + beanName + '\'' +
                ", clzzName='" + clzzName + '\'' +
                ", methodName='" + methodName + '\'' +
                '}';
    }

    @Override
    public String transportId() {
        return getId();
    }
}
