package com.secrething.learn.algorithm;

/**
 * Created by liuzz on 2019-03-27 10:26.
 */
public class LengthOfLongestSubString {
    public static int lengthOfLongestSubstring(String s) {
        int l = s.length();
        if (l < 2) {
            return l;
        }
        int max = 0;
        int[] iarr = new int[128];
        int lef = 0;
        int ri = 0;
        for (int i = 0, j = 0; j < l; ++j) {
            char cha = s.charAt(j);
            i = max(i,iarr[cha]);
            int m = max(max, (j - i+1));
            if (m > max){
                max = m;
                lef = i;
                ri = j;
            }
            iarr[cha] = j +1;
        }
        System.out.println(String.format("left:%d,right:%d",lef,ri));
        return max;
    }

    private static int max(int i, int j) {
        return i < j ? j : i;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abbbbcdefabcdej"));
    }
}
