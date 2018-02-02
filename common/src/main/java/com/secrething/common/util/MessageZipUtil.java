package com.secrething.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by liuzengzeng on 2017/12/10.
 */
public class MessageZipUtil {
    /**
     * 压缩
     * @param encode
     * @return
     */
    public static String zipEncode(String encode){
        byte[] pureResult;
        try {
            pureResult = encode.getBytes("UTF-8");
            ByteArrayInputStream inputStream =
                    new ByteArrayInputStream(pureResult);
            ByteArrayOutputStream miwenOutput=new ByteArrayOutputStream();
            GZIPOutputStream outStream =
                    new GZIPOutputStream ( miwenOutput);

            byte[] buf = new byte[10000];
            while (true) {
                int size = inputStream.read(buf);
                if (size <= 0)
                    break;
                outStream.write(buf, 0, size);
            }
            outStream.close();
            inputStream.close();
            byte[] miwenbytes=miwenOutput.toByteArray();
            String pursf=Base64Util.encode2Str(miwenbytes);
            return pursf;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解压缩
     * @param decode
     * @return
     */
    public static String zipDecode(String decode){
        byte[] pureResult;
        try {
            pureResult = Base64Util.decode2ByteArray(decode);
            ByteArrayOutputStream outStream =
                    new ByteArrayOutputStream(10 * pureResult.length);
            GZIPInputStream inStream =
                    new GZIPInputStream ( new ByteArrayInputStream(pureResult) );
            byte[] buf = new byte[10000];
            while (true) {
                int size = inStream.read(buf);
                if (size <= 0)
                    break;
                outStream.write(buf, 0, size);
            }
            outStream.close();
            inStream.close();
            String pursf=new String(outStream.toByteArray(),"UTF-8");
            return pursf;

        } catch (Exception e) {
            return null;
        }

    }
}
