package com.secrething.common;

import com.secrething.common.core.Document;
import com.secrething.common.core.Entry;
import com.secrething.common.util.UUIDBuilder;
import lombok.Data;

/**
 * Created by liuzengzeng on 2018-11-25.
 */
@Document(index = "adrift",type = "message")
@Data
public class Message {

    @Entry(key = "id")
    private String uid = UUIDBuilder.genUUID();

    @Entry
    private String name;
    @Entry
    private String content;
}
