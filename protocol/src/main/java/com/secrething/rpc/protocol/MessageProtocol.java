package com.secrething.rpc.protocol;


import com.secrething.common.contants.Constant;

/**
 * Created by liuzengzeng on 2017/12/11.
 * 通信协议
 */
public class MessageProtocol {
    /**
     * 消息的开头的信息标志
     */
    private int head = Constant.HEAD_DATA;

    /**
     * 消息的长度
     */
    private int contentLength;

    /**
     * 消息的内容
     */
    private byte[] content;

    /**
     * 用于初始化，org.secret.message.lib.SmartCarProtocol
     *
     * @param content 协议里面，消息的数据
     */
    public MessageProtocol(byte[] content) {
        this.content = content;
        this.contentLength = content.length;
    }

    public MessageProtocol(Object obj) {
        this(ProtostuffSerializer.getInstance(), obj);
    }

    public MessageProtocol(Serializer serializer, Object obj) {
        this(serializer.encode(obj));
    }

    public int getHead() {
        return head;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageProtocol{" +
                "head='" + head + '\'' +
                ", contentLength=" + contentLength +
                '}';
    }
}
