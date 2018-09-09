package com.secrething.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Idroton on 2018/9/9 2:40 PM.
 */
public class Validate {
    public static <T> boolean notEmpty(T[] arr) {
        return null != arr && arr.length > 0;
    }

    public static <T> boolean notEmpty(Collection<T> arr) {
        return null != arr && arr.size() > 0;
    }

    public static <K, V> boolean notEmpty(Map<K, V> arr) {
        return null != arr && arr.size() > 0;
    }
}
