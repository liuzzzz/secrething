package com.secrething.rpc.protocol;


import com.secrething.common.contants.Constant;

/**
 * Created by liuzengzeng on 2017/12/11.
 * 通信协议
 */
public class MessageProtocol {
    public static final int DEFAULT = 0;
    public static final int PROXY = 1;
    public static final int HEART = 2;
    private int messageType = 0;//默认0,1代理,2心跳
    private long messageUID;
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
     * @param contentLength 协议里面，消息数据的长度
     * @param content       协议里面，消息的数据
     */
    public MessageProtocol(int contentLength, byte[] content) {
        this.contentLength = contentLength;
        this.content = content;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public long getMessageUID() {
        return messageUID;
    }

    public void setMessageUID(long messageUID) {
        this.messageUID = messageUID;
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
