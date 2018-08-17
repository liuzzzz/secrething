package com.secrething.rpc.core;

import lombok.Data;

/**
 * Created by liuzengzeng on 2017/12/18.
 * 通信请求封装
 */
@Data
public class RemoteRequest {
    public static final int PROXY = 0;
    public static final int HEART = 1;
    private final int type;
    private String requestId;
    private String beanName;
    private String clzzName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
    public RemoteRequest(int type) {
        this.type = type;
    }


}
