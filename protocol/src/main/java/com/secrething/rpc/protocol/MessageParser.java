package com.secrething.rpc.protocol;

import com.secrething.common.contants.Constant;
import io.netty.buffer.ByteBuf;

/**
 * Created by Idroton on 2018/8/12.
 */
public class MessageParser {

    public static MessageProtocol parse(ByteBuf buffer, int minLength) {
        // 防止socket字节流攻击
        // 防止，客户端传来的数据过大
        // 因为，太大的数据，是不合理的,有时候必须大
            /*if (buffer.readableBytes() > 2048) {
                buffer.skipBytes(buffer.readableBytes());
            }*/

        // 记录包头开始的index
        int beginReader;

        while (true) {
            // 获取包头开始的index
            beginReader = buffer.readerIndex();
            // 标记包头开始的index
            buffer.markReaderIndex();
            // 读到了协议的开始标志，结束while循环

            if (Constant.HEAD_DATA == buffer.readInt()) {
                break;
            }

            // 未读到包头，略过一个字节
            // 每次略过，一个字节，去读取，包头信息的开始标记
            buffer.resetReaderIndex();
            buffer.readByte();

            // 当略过，一个字节之后，
            // 数据包的长度，又变得不满足
            // 此时，应该结束。等待后面的数据到达
            if (buffer.readableBytes() < minLength) {
                return null;
            }
        }
        // 消息的长度
        int length = buffer.readInt();
        // 判断请求数据包数据是否到齐
        if (buffer.readableBytes() < length) {
            // 还原读指针
            buffer.readerIndex(beginReader);
            return null;
        }

        // 读取data数据
        byte[] data = new byte[length];
        buffer.readBytes(data);
        MessageProtocol protocol = new MessageProtocol(data);
        return protocol;
    }

}
