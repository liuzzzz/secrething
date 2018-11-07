package com.secrething.learn.algorithm;

/**
 * Created by Idroton on 2018/9/24 4:02 PM.
 */
public class KMPTest {


    /**
     * 构建tab
     * abcdabe
     * a     0
     * ab a|b 0
     * abc a/c,ab/bc 0
     * abcd a/d,ab/cd,abc/bcd 0
     * abcda a/a,ab/da,abc/cda,abcd/bcda 1
     * abcdab a/b,ab/ab,abc/dab,abcd/cdab,abcda/bcdab 2
     * abcdabe 0
     * [-1,0,0,0,1,2,0]
     * @param arr
     * @return
     */
    private static int[] buildTab(char[] arr) {

        int[] tab = new int[arr.length];
        tab[0] = -1;
        int l = 0;
        for (int i = 1; i < arr.length; i++) {
            while (l >0 && arr[i] != arr[l]){
                l = tab[l-1];
            }
            if (arr[i] == arr[l]){
                l++;
            }
            tab[i] = l;
        }
        return tab;
    }
    public static int kmpMatch(String major,String pattern){
        char[] mArr = major.toCharArray();
        char[] p = pattern.toCharArray();
        int[] tab = buildTab(p);
        int idx = 0;
        for (int i = 0; i < mArr.length ; i++) {
            while (idx > 0 && mArr[i] != p[idx]){
                idx = tab[idx-1];
            }
            if (mArr[i] == p[idx]){
                idx++;
            }
            if (idx == p.length){
                return i+1-p.length;
            }
        }

        return -1;
    }
    public static void main(String[] args) {
       // System.out.println(kmpMatch("abcab","cab"));
        //System.out.println(Arrays.toString(buildTab("abcdabe".toCharArray())));
        System.out.println(Thread.currentThread().getId());
    }
}
