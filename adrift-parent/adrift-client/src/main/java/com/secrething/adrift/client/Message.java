package com.secrething.adrift.client;

import com.secrething.common.core.Document;
import lombok.Data;

import java.util.UUID;

/**
 * Created by liuzz on 2018/11/22 下午4:36.
 */
@Data
@Document(index = "adrift", type = "message")
public class Message {
    private String uid = UUID.randomUUID().toString();
    private String name;
    private String content;


}
