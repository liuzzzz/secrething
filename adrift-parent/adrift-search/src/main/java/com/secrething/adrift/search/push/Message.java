package com.secrething.adrift.search.push;

import com.alibaba.fastjson.JSONObject;
import com.secrething.adrift.search.core.Routing;
import com.secrething.adrift.search.push.protocol.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzz on 2018-12-08 15:42.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int code;
    private String body;
    private Map<String, Object> extension = new HashMap<>();


}
