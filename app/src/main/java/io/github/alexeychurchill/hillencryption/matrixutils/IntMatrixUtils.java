package io.github.alexeychurchill.hillencryption.matrixutils;

import android.util.Log;

import java.util.Arrays;

/**
 * Matrix utils
 * Set of the matrix utilities
 */

public class IntMatrixUtils {
    public static int[][] getSquareMatrix(int size) {
        return new int[size][size];
    }

    public static int[][] getColumnMatrix(int size) {
        return new int[size][1];
    }

    public static int[][] multiply(int[][] a, int[][] b) {
        if (a.length < 1 || b.length < 1) {
            throw new IndexOutOfBoundsException("Wrong matrix size");
        }
        if (a[0].length < 1 || b[0].length < 1) {
            throw new IndexOutOfBoundsException("Wrong matrix size");
        }
        if (a[0].length != b.length) {
            throw new IndexOutOfBoundsException("Incompatible matrices");
        }
        int[][] result = new int[a.length][b[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    public static int[][] multiply(int[][] a, int[][] b, int m) {
        int[][] multiplied = multiply(a, b);
        for (int i = 0; i < multiplied.length; i++) {
            for (int j = 0; j < multiplied[i].length; j++) {
                multiplied[i][j] = multiplied[i][j] % m;
            }
        }
        return multiplied;
    }

    public static int[][] minor(int[][] matrix, int i, int j) {
        if (matrix.length < 2 || matrix[0].length < 2) {
            throw new IndexOutOfBoundsException("Wrong matrix size");
        }
        if (i >= matrix.length || j >= matrix[0].length || i < 0 || j < 0) {
            throw new IndexOutOfBoundsException("Wrong minor index");
        }
        int[][] result = new int[matrix.length - 1][matrix[0].length - 1];
        for (int iMinor = 0; iMinor < matrix.length; iMinor++) {
            for (int jMinor = 0; jMinor < matrix[iMinor].length; jMinor++) {
                if (iMinor < i && jMinor < j) { //Upper left
                    result[iMinor][jMinor] = matrix[iMinor][jMinor];
                }
                if (iMinor < i && jMinor > j) { //Upper right
                    result[iMinor][jMinor - 1] = matrix[iMinor][jMinor];
                }
                if (iMinor > i && jMinor < j) { //Bottom left
                    result[iMinor - 1][jMinor] = matrix[iMinor][jMinor];
                }
                if (iMinor > i && jMinor > j) { //Bottom right
                    result[iMinor - 1][jMinor - 1] = matrix[iMinor][jMinor];
                }
            }
        }
        return result;
    }

    public static int det(int[][] matrix) {
        if (matrix.length < 1 || matrix[0].length < 1) {
            throw new IndexOutOfBoundsException("Wrong matrix size");
        }
        if (matrix.length == 1 || matrix[0].length == 1) {
            return matrix[0][0];
        }
        int det = 0;
        for (int j = 0; j < matrix[0].length; j++) {
            int[][] minorMatrix = minor(matrix, 0, j);
            int minorDet = det(minorMatrix);
            int element = matrix[0][j] * sign(0, j) * minorDet;
            det += element;
        }
        return det;
    }

    public static int[][] allied(int[][] matrix) {
        if (matrix.length < 1 || matrix[0].length < 1) {
            throw new IndexOutOfBoundsException("Wrong matrix size");
        }
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[i][j] = sign(i, j) * det(minor(matrix, i, j));
            }
        }
        return result;
    }

    public static int[][] transpose(int[][] matrix) {
        if (matrix.length < 1 || matrix[0].length < 1) {
            throw new IndexOutOfBoundsException("Wrong matrix size");
        }
        int[][] result = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = matrix[j][i];
            }
        }
        return result;
    }

    public static int sign(int i, int j) {
        return ((i + j + 2) % 2 == 0) ? 1 : -1;
    }

    public static int[][] inverseByMod(int[][] matrix, int m) {
        if (matrix.length < 1 || matrix[0].length < 1) {
            throw new IndexOutOfBoundsException("Wrong matrix size");
        }
        int matrixDet = IntMatrixUtils.det(matrix);
        if (matrixDet == 0) {
            return null;
        }
        if (!NumberUtils.isInverseByModExists(matrixDet, m)) {
            return null;
        }
        matrixDet = NumberUtils.mod(matrixDet, m);
        matrixDet = NumberUtils.inverseByMod(matrixDet, m);
        int[][] result = IntMatrixUtils.transpose(IntMatrixUtils.allied(matrix));
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = NumberUtils.mod(result[i][j] * matrixDet, m);
            }
        }
        return result;
    }

    public static void printMatrixToLog(String tag, int[][] matrix) {
        if (matrix == null || tag == null) {
            return;
        }
        Log.d(tag, Arrays.deepToString(matrix));
    }
}
