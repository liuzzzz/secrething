package com.secrething.esutil.core;

import java.util.Map;

/**
 * Created by liuzz on 2018/11/25 上午12:36.
 */
public class Record {
    private String index;
    private String type;
    private String id;
    private Map source;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map getSource() {
        return source;
    }

    public void setSource(Map source) {
        this.source = source;
    }

    @SuppressWarnings("unchecked")
    public static Record create(Object obj, String uid) {
        Class clzz = obj.getClass();
        try {
            if (!clzz.isAnnotationPresent(Document.class))
                throw new UnsupportedOperationException("not mark Document");
            Document document = (Document) clzz.getAnnotation(Document.class);
            Record record = new Record();
            record.setIndex(document.index());
            record.setType(document.type());
            Map<String, Object> s = MapWriter.map(obj);
            if (null != s) {
                Object id = s.remove("id");
                if (StringUtils.isBlank(uid) && null != id) {
                    record.setId(id.toString());
                }
                record.setSource(s);
            }
            return record;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Record create(Object obj) {
        return create(obj, null);
    }
}
