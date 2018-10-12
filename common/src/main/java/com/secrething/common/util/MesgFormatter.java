package com.secrething.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author liuzz
 * @create 2018/3/14
 */
public abstract class MesgFormatter {
    private static final String PLACE_HOLDER = "{}";
    static int split = PLACE_HOLDER.length();

    private MesgFormatter() {
        throw new UnsupportedOperationException("");
    }

    /**
     * 字符串拼接,拼接方式如同slf4j日志的输出方式
     * 例: format("h{}ll{}","e","o") return hello
     *
     * @param pattern
     * @param params
     * @return
     */
    public static final String format(String pattern, Object... params) {
        return formatWithHolder(pattern,PLACE_HOLDER,params);
    }

    public static final String formatWithHolder(String pattern, String hoder, Object... params) {
        if (params.length < 1 || StringUtils.isBlank(pattern))
            return pattern;
        char[] src = pattern.toCharArray();
        int offset = 0;
        int idx = pattern.indexOf(hoder, offset);
        if (idx < 0)
            return pattern;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < params.length && idx > -1; i++) {
            int len = idx - offset;
            if (len > 0)
                builder.append(src, offset, len);
            builder.append(params[i]);
            offset = idx + split;
            idx = pattern.indexOf(hoder, offset);
        }
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }

    public static final void println(String pattern, Object... params) {
        System.out.println(format(pattern, params));
    }
    public static final void println(Object params) {
        System.out.println(format("{}", params));
    }
    public static final void print(String pattern, Object... params) {
        System.out.print(format(pattern, params));
    }
}
