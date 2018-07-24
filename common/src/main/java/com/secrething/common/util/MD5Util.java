package com.secrething.common.util;

import java.security.MessageDigest;

/**
 * Created by liuzengzeng on 2017/12/22.
 */
public class MD5Util {
    static MessageDigest md5 = null;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            md5 = null;
        }
    }

    public static String md5Gen(String origin) {
        try {
            CharSequence sequence = doMd5(origin);
            return sequence.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static CharSequence doMd5(String origin) throws Exception{
        StringBuilder hexValue = new StringBuilder();
        try {
            byte[] byteArray = origin.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);

            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue;
        }catch (Exception e){
           throw e;
        }

    }
    public static String md5Gen16B(String origin) {
        try {
            CharSequence hexValue = doMd5(origin);
            return hexValue.toString().substring(8, 24);

        } catch (Exception e) {
            return null;
        }
    }
}
