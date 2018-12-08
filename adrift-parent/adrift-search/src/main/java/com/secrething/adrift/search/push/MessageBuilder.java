package com.secrething.adrift.search.push;

import com.alibaba.fastjson.JSONObject;
import com.secrething.adrift.search.core.Routing;
import com.secrething.adrift.search.push.protocol.Constants;

import java.util.List;

/**
 * Created by liuzz on 2018-12-08 15:42.
 */
public class MessageBuilder {
    public static String buildSystemMsg(Object msg) {
        Message m = new Message();
        m.setCode(Constants.SYS_OTHER_INFO);
        m.getExtension().put("code", Constants.SYS_OTHER_INFO);
        m.getExtension().put("msg", msg);
        return JSONObject.toJSONString(m);
    }

    public static String buildRoutingsMsg(String searchKey, List<Routing> msg) {
        Message m = new Message();
        m.setCode(Constants.SEARCH_CODE);
        m.getExtension().put("code", Constants.SEARCH_CODE);
        m.getExtension().put("k", searchKey);
        m.getExtension().put("msg", msg);
        return JSONObject.toJSONString(m);
    }
}
