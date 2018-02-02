package com.secrething.rpc.server;

import com.secrething.common.util.SerializeUtil;
import com.secrething.rpc.core.RemoteRequest;
import com.secrething.rpc.core.RemoteResponse;
import com.secrething.rpc.protocol.MessageProtocol;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.util.Map;

/**
 * Created by liuzengzeng on 2017/12/20.
 * Server通信handler
 */
public class ServerSocketHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private final Map<String, Object> handlerMap;

    public ServerSocketHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, final MessageProtocol msg) throws Exception {
        Server.submit(new Runnable() {
            @Override
            public void run() {
                RemoteRequest request = SerializeUtil.deserialize(msg.getContent(), RemoteRequest.class);
                RemoteResponse response = new RemoteResponse();
                response.setRequestId(request.getRequestId());
                try {
                    Object result = handle(request);
                    response.setResult(result);
                } catch (Throwable e) {
                    response.setError(e.toString());
                }
                byte[] data = SerializeUtil.serialize(response);
                MessageProtocol out = new MessageProtocol(data.length,data);
                ctx.writeAndFlush(out).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    }
                });

            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    private Object handle(RemoteRequest request) throws Throwable {
        String beanName = request.getBeanName();
        Object serviceBean = handlerMap.get(beanName);
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }
}
