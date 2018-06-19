package com.secrething.rpc.server;

import com.secrething.common.util.Assert;
import com.secrething.common.util.DataContainer;
import com.secrething.common.util.SerializeUtil;
import com.secrething.rpc.core.RemoteRequest;
import com.secrething.rpc.core.RemoteResponse;
import com.secrething.rpc.protocol.MessageProtocol;
import com.secrething.rpc.proxy.ProxyOperation;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by liuzengzeng on 2017/12/20.
 * Server通信handler
 */
public class ServerSocketHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private final DataContainer<String, ProxyOperation> handlerMap;

    ServerSocketHandler(DataContainer<String, ProxyOperation> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, final MessageProtocol msg) throws Exception {
        Server.submit(() -> {
            RemoteRequest request = SerializeUtil.deserialize(msg.getContent(), RemoteRequest.class);
            Assert.notNull(request);
            RemoteResponse response = new RemoteResponse();
            response.setRequestId(request.getRequestId());
            try {
                Object result = handle(request);
                response.setResult(result);
            } catch (Throwable e) {
                response.setThrowable(e);
            }
            byte[] data = SerializeUtil.serialize(response);
            Assert.notNull(data);
            MessageProtocol out = new MessageProtocol(data.length,data);
            ctx.writeAndFlush(out).addListener((ChannelFutureListener) channelFuture -> {
            });

        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    private Object handle(RemoteRequest request) throws Throwable {
        String beanName = request.getBeanName();
        String methodName = request.getMethodName();
        Object[] parameters = request.getParameters();
        ProxyOperation operation = handlerMap.getNodeData(beanName,methodName);
        Assert.notNull(operation);
        return operation.invoke(parameters);//serviceFastMethod.invoke(serviceBean, parameters);
    }
}
