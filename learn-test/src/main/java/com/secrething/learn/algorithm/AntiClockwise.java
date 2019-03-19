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

    public static void main(String[] args) {
        int[][] matrix = antiClockwiseMatrix(1,9);
        Matrix.printMatrix(matrix);
    }
}
