package com.secrething.learn.interfaces;

/**
 * Created by liuzz on 2019-03-14 16:37.
 */
public interface Intercepter {

    <T> T pre(PreWrapper preWrapper);

    <T> T after(AfterWrapper<?> afterWrapper);
}
