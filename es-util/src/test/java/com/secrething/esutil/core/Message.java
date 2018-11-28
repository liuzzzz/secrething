package com.secrething.esutil.core;

import java.util.Date;

/**
 * Created by liuzz on 2018-11-27 13:12.
 */
@Document(index = "wxrobot", type = "message")
public class Message extends Base {

    @Key("msg_id")
    private long msgId;
    @Key("wx_id")
    private String wxId;
    @Key("fwx_id")
    private String fwxId;
    @Key("msg_type")
    private int msgType;
    @Key("state")
    private int state;
    @Key("is_send")
    private int isSend;
    @Key("content")
    private String content;
    @Key("create_time")
    private String createTime;
    @Key("last_modify_time")
    private Date lastModifyTime;

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getFwxId() {
        return fwxId;
    }

    public void setFwxId(String fwxId) {
        this.fwxId = fwxId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}
