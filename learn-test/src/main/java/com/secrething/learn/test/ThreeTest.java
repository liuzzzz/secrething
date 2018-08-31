package com.secrething.learn.test;

/**
 * Created by liuzz on 2018/8/30 00:07 AM.
 */
public class ThreeTest {



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

    public static void main(String[] args) {
        System.out.println(inputThree("002021000123"));
    }
}
