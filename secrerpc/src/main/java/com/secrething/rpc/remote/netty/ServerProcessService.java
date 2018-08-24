package com.secrething.rpc.remote.netty;

import com.secrething.rpc.core.RemoteRequest;
import com.secrething.rpc.core.RemoteResponse;
import com.secrething.rpc.protocol.ProcessService;
import com.secrething.rpc.registry.ServiceLocalStorage;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * Created by Idroton on 2018/8/17 10:37 PM.
 */
@Slf4j
public class ServerProcessService implements ProcessService<RemoteRequest, RemoteResponse> {
    @Override
    public RemoteResponse process(RemoteRequest inputMsg) {
        RemoteResponse response = new RemoteResponse();
        response.setRequestId(inputMsg.getRequestId());
        try {
            Object result = invoke(inputMsg);
            response.setResult(result);
        } catch (Exception e) {
            log.error("request invoke fail", e);
            response.setError("error");
            response.setThrowable(e);
        }
        return response;
    }

    private Object invoke(RemoteRequest request) throws Exception {
        Object serviceImpl = ServiceLocalStorage.borrow(request.getBeanName());
        if (null == serviceImpl)
            serviceImpl = ServiceLocalStorage.borrow(request.getClzzName());
        if (null == serviceImpl) {
            throw new Exception("service class not found");
        }
        Class clzz = serviceImpl.getClass();
        Method m = clzz.getDeclaredMethod(request.getMethodName(), request.getParameterTypes());
        if (null == m) {
            throw new Exception("service method not found");
        }
        return m.invoke(serviceImpl, request.getParameters());
    }
}
