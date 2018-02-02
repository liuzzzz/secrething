package com.secrething.rpc.protocol;


import com.secrething.rpc.registry.Constant;

/**
 * Created by liuzengzeng on 2017/12/11.
 * 通信协议
 */
public class MessageProtocol {
    /**
     * 消息的开头的信息标志
     */
    private int head_data = Constant.HEAD_DATA;

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

    public int getHead_data() {
        return head_data;
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
                "head_data='" + head_data + '\'' +
                ", contentLength=" + contentLength +
                '}';
    }
}
