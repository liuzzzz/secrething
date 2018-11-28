package com.secrething.esutil.core;

import java.util.Date;

/**
 * Created by liuzz on 2018-11-27 12:07.
 */
public class Base {
    @Key("id")
    private String uid = String.valueOf(System.currentTimeMillis());

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    Object format(java.util.Map map, com.secrething.esutil.core.DateParser p) throws Exception {
        com.secrething.esutil.core.Message obj = new com.secrething.esutil.core.Message();
        if (null == map) {
            return obj;
        }
        com.secrething.esutil.core.DateParser parser = p;
        if (null == parser) {
            parser = com.secrething.esutil.core.DatePaserEnum.LONG;
        }
        Object v_0 = map.get("msg_id");
        if (null != v_0) {
            long v = Long.valueOf(v_0.toString()).longValue();
            obj.setMsgId(v);
        }
        Object v_1 = map.get("wx_id");
        if (null != v_1) {
            java.lang.String v = (java.lang.String) v_1.toString();
            obj.setWxId(v);
        }
        Object v_2 = map.get("fwx_id");
        if (null != v_2) {
            java.lang.String v = (java.lang.String) v_2.toString();
            obj.setFwxId(v);
        }
        Object v_3 = map.get("msg_type");
        if (null != v_3) {
            int v = Integer.valueOf(v_3.toString()).intValue();
            obj.setMsgType(v);
        }
        Object v_4 = map.get("state");
        if (null != v_4) {
            int v = Integer.valueOf(v_4.toString()).intValue();
            obj.setState(v);
        }
        Object v_5 = map.get("is_send");
        if (null != v_5) {
            int v = Integer.valueOf(v_5.toString()).intValue();
            obj.setIsSend(v);
        }
        Object v_6 = map.get("content");
        if (null != v_6) {
            java.lang.String v = (java.lang.String) v_6.toString();
            obj.setContent(v);
        }
        Object v_7 = map.get("create_time");
        if (null != v_7) {
            java.lang.String v = (java.lang.String) v_7.toString();
            obj.setCreateTime(v);
        }
        Object v_8 = map.get("last_modify_time");
        if (null != v_8) {
            java.lang.Object vvv = (java.lang.Object) v_8;
            java.util.Date v = parser.parse(vvv);
            obj.setLastModifyTime(v);
        }
        Object v_9 = map.get("id");
        if (null != v_9) {
            java.lang.String v = (java.lang.String) v_9.toString();
            obj.setUid(v);
        }
        return obj;
    }
}
