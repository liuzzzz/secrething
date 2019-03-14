package com.secrething.esutil.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

/**
 * Created by liuzz on 2018-11-27 12:07.
 */
public class Base {
    static class Solution {
        public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
            if(null != nums1 && null == nums2){
                return onlyOneNull(nums1);
            }else if(null == nums1 && null != nums2){
                return onlyOneNull(nums2);
            }else{
                int size1 = nums1.length;
                int size2 = nums2.length;
                int total = size1 + size2;
                boolean b = total % 2 == 0;
                int m = total >> 1;
                int li1 = size1 - 1;
                int li2 = size2 - 1;
                if(nums1[li1] <= nums2[0]){
                    return case1(nums1,nums2,b,li1,m);
                }else if(nums2[li2] <= nums1[0]){
                    return case1(nums2,nums1,b,li2,m);
                }else{
                    int left1 = 0;
                    int right1 = li1+1;
                    int left2 = 0;
                    int right2 = li2+1;
                    int mid1 = (left1+right1) >> 1;
                    int mid2 = (left2+right2) >> 1;
                    while (nums2[mid2] > nums1[mid1] || nums1[mid1] > nums2[mid2]){
                        if (nums2[mid2] > nums1[mid1]){
                            right2 = mid2;
                            left1 = mid1;
                        }
                        if (nums1[mid1] > nums2[mid2]){
                            right1 = mid1;
                            left2 = mid2;
                        }
                        mid1 = (left1 + right1) >> 1;
                        mid2 = (left2 + right2) >> 1;
                    }
                    return binaryCount(nums1[mid1],nums2[mid2]);

                }
            }
        }
        public static double case1(int[] nums1,int[] nums2,boolean b,int li,int m){
            if(b){
                if (m > nums1.length){
                    int m1 = m-nums1.length;
                    return binaryCount(nums2[m1],nums2[m1-1]);
                }else if (m <= li){
                    return binaryCount(nums1[m],nums1[m-1]);
                }else{
                    return binaryCount(nums1[li],nums2[0]);
                }
            }else{
                if(m <= li){
                    return Double.valueOf(nums1[m-1]);
                }else{
                    return Double.valueOf(nums2[m-1-nums1.length]);
                }
            }
        }
        public static double binaryCount(int m1,int m2){
            return (Double.valueOf(m1)+Double.valueOf(m2))/2;
        }

        public static double onlyOneNull(int[] nums){
            int size = nums.length;
            int m = size >> 1;
            if(size % 2 == 0){
                return binaryCount(nums[m],nums[m-1]);
            }else{
                return Double.valueOf(nums[m]);
            }
        }
    }
    private static byte[] bs = new byte[900000];
    public static void main(String[] args) throws Exception {
        int il = pre();
        long l = Runtime.getRuntime().freeMemory();
        System.out.println(l);
        Thread.sleep(1000);
        //for (int i = 0; i <3 ; i++) {
            //byte[] b = new byte[1210001];
            for (;;) {
                byte[]  b = new byte[3000000];
                b = null;
                Thread.sleep(1000);

            }
        /*for (int i = 0; i <3 ; i++) {
            byte[] b = new byte[1210001];
            b = null;
        }*/
            //b = null;
       // }

    }
    static int pre() throws IOException {
        String name = ManagementFactory.getRuntimeMXBean().getName();

        final String pid = name.split("@")[0];
        System.out.println(pid);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Process process = null;
                try {
                    process = Runtime.getRuntime().exec("jstat -gcutil "+pid+" 500");
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    for (String s = br.readLine();null != s;s = br.readLine()){
                        System.out.println(s);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

        long l = Runtime.getRuntime().freeMemory();
        int il = Integer.valueOf(String.valueOf(l));
        System.out.println(l);
        return il;
    }
}
