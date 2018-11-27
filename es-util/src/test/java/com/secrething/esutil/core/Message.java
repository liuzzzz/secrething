package com.secrething.esutil.core;

/**
 * Created by liuzz on 2018-11-27 13:12.
 */
@Document(index = "wxrobot", type = "message")
public class Message extends Base {

    @Key
    private boolean send;

    @Key
    private String content;

    @Key
    private long sendTime = System.currentTimeMillis();

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
