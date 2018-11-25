package com.secrething.common;

import com.secrething.common.core.Document;
import com.secrething.common.core.DocField;
import com.secrething.common.util.UUIDBuilder;
import lombok.Data;

/**
 * Created by liuzengzeng on 2018-11-25.
 */
@Document(index = "adrift",type = "message")
@Data
public class Message {

    @DocField(key = "id")
    private String uid = UUIDBuilder.genUUID();

    @DocField
    private String name;
    @DocField
    private String content;
}
