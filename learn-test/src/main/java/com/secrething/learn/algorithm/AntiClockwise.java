package com.secrething.learn.algorithm;

/**
 * Created by liuzz on 2019-03-18 17:24.
 */
public class AntiClockwise {

    public static int[][] antiClockwiseMatrix(int beginCount, int level) {
        int[][] matrix = new int[level][level];
        int layer = level >> 1;

        for (int i = 0; i < layer; i++) {
            beginCount = buildOneLayer(matrix, i, beginCount);
        }
        if (level % 2 != 0) {
            matrix[layer][layer] = beginCount;
        }


        return matrix;
    }

    private static int buildOneLayer(int[][] matrix, int layer, int beginCount) {
        int begin = beginCount;
        for (int i = layer; i < matrix.length - layer; i++) {
            matrix[layer][i] = begin;
            ++begin;
        }
        for (int i = layer + 1; i < matrix.length - layer; i++) {
            matrix[i][matrix.length - 1 - layer] = begin;
            ++begin;
        }

        for (int i = matrix.length - 2 - layer; i >= layer; --i) {
            matrix[matrix.length - 1 - layer][i] = begin;
            ++begin;
        }
        for (int i = matrix.length - 2 - layer; i > layer; --i) {
            matrix[i][layer] = begin;
            ++begin;
        }


        return begin;


    }
    static void  build_matrix(int n)
    {

        int[][] arr = new int[n][n];// = (int**)malloc(sizeof(int) * n );
        int layer = n >> 1;
        int idx = 0;
        for(int i = 0 ; i < layer ; i++){
            int j;
            // 向右
            for(j=i; j < (n - i);j++){
                arr[i][j] = ++idx;
            }
            //向下
            for(j=i+1; j< (n-i); j++){
                arr[j][n-1-i] = ++idx;
            }

            //向左
            for(j=(n-2-i); j >= i; j--){
                arr[n-1-i][j] = ++idx;
            }

            //向上

            for(j=(n-2-i); j> i; j--){
                arr[j][i] = ++idx;
            }
            for (int l = 0; l< 6; l++) {
                for (int m = 0; m < 6; m++) {
                    System.out.printf("%d \t",arr[l][m]);
                }
                System.out.println();
            }
        }
        if ( n % 2 != 0) {
            arr[layer][layer] = ++idx;
        }

        for (int i = 0; i< 6; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.printf("%d \t",arr[i][j]);
            }
            System.out.println();
        }


    }


    public static void main(String[] args) {
        //int[][] matrix = antiClockwiseMatrix(1,9);
        //Matrix.printMatrix(matrix);
        build_matrix(6);
    }
}
