package com.secrething.common;

import com.secrething.common.core.Document;
import com.secrething.common.core.Entry;
import com.secrething.common.util.UUIDBuilder;
import lombok.Data;

import java.util.Objects;

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
    @Entry
    private int len = 20;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return len == message.len &&
                uid.equals(message.uid) &&
                Objects.equals(name, message.name) &&
                Objects.equals(content, message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, name, content, len);
    }
}
