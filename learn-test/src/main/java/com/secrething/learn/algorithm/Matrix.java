package com.secrething.learn.algorithm;

/**
 * Created by liuzz on 2019-03-18 18:03.
 */
public class Matrix {

    public static void printMatrix(int[][] matrix) {
        for (int j = 0; j < matrix.length; j++) {
            for (int k = 0; k < matrix.length; k++) {
                System.out.print(matrix[j][k]);
                System.out.print("\t");
            }
            System.out.println();
        }
    }
}
