package com.secrething.common.core;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by liuzz on 2018/11/25 上午12:36.
 */
@Data
public class Record {
    private String index;
    private String type;
    private String id;
    private Map source;

    public static Record build(Object obj, String uid) {
        Class clzz = obj.getClass();
        try {
            MapWriter writer = MapWriter.getWriter(clzz);
            if (!clzz.isAnnotationPresent(Document.class))
                throw new UnsupportedOperationException("not mark Document");
            Document document = (Document) clzz.getAnnotation(Document.class);
            Record record = new Record();
            record.setIndex(document.index());
            record.setType(document.type());
            Map s = (Map) writer.toMap(obj);
            Object id = s.remove("id");
            if (StringUtils.isBlank(uid))
                record.setId(id.toString());
            record.setSource(s);
            return record;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public static Record build(Object obj) {
        return build(obj, null);
    }

    public static void main(String[] args) {
        Record r = build(new TestDoc());
        System.out.println(r);
    }
}
