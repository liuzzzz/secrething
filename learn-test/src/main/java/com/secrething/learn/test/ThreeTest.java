package com.secrething.learn.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by liuzz on 2018/8/30 00:07 AM.
 */
public class ThreeTest {

/**/

    public static int inputThree(String num){
        int idx = 0;
        int count = 0;
        int n = Integer.parseInt(num);
        if (0 == n){
            count = num.length();
        }else {
            String checkStr = String.valueOf(n);
            int ckLen = checkStr.length();
            int nlen = num.length();
            idx =count = nlen-ckLen;
            for (int i = idx; i < nlen; i++) {
                String currOneBitStr = num.substring(i,i+1);
                int currOneBitNum = Integer.parseInt(currOneBitStr);
                if ((currOneBitNum % 3) == 0){
                    idx = i+1;
                    ++count;
                    continue;
                }
                String currStr = num.substring(idx,i+1);
                int currNum = Integer.parseInt(currStr);
                if ((currNum % 3) == 0){
                    idx = i+1;
                    ++count;
                }
            }
        }
        return count;
    }
    //统计java文件有多少行注释
    public static int calculateAnno(File file){
        int count = 0;
        if (null == file || !file.getName().endsWith(".java")){
            return count;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;

            boolean isMulti = false;
            while ((line = reader.readLine()) != null){
                if (line.indexOf("//") > -1){
                    count++;
                    continue;
                }
                if (line.indexOf("/*") > -1){
                    if (line.indexOf("*/") > -1){
                        count++;
                        continue;
                    }
                    isMulti = true;
                }
                if (isMulti){
                    count++;
                }
                if (line.indexOf("*/") > -1){
                    isMulti = false;
                }
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;

    }
    public static void main(String[] args){
        File file = new File("/Users/Idroton/workspace/secrething/learn-test/src/main/java/com/secrething/learn/test/ByteCodeTest.java");
        System.out.println(calculateAnno(file));

    }
}
