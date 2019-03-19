package com.secrething.learn.algorithm;

import static com.secrething.learn.algorithm.Matrix.printMatrix;

/**
 * Created by liuzz on 2019-03-18 16:11.
 */
public class RollMatrix {

    public static void rollMatrix(int[][] matrix) {
        int level = matrix.length >> 1;
        for (int i = 0; i < level; i++) {
            rotate(matrix, i);
        }
    }

    private static void rotate(int[][] matrix, int level) {
        for (int i = level; i < matrix.length -1- level; i++) {
            int tmp = matrix[level][i];
            matrix[level][i] = matrix[matrix.length - 1 - i][level];
            matrix[matrix.length - 1 - i][level] = matrix[matrix.length - 1 - level][matrix.length - 1 - i];
            matrix[matrix.length - 1 - level][matrix.length - 1 - i] = matrix[level + i][matrix.length - 1 - level];
            matrix[level + i][matrix.length - 1 - level] = tmp;
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[4][4];
        int i = 1;
        for (int j = 0; j < matrix.length; j++) {
            for (int k = 0; k < matrix.length; k++) {
                matrix[j][k] = i;
                ++i;
            }
        }
        printMatrix(matrix);
        rollMatrix(matrix);

        printMatrix(matrix);
    }


}
