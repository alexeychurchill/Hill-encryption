package io.github.alexeychurchill.hillencryption.matrixutils;

import java.util.Arrays;

public class IntMatrix {
    private int[][] matrix;

    public IntMatrix(int n, int m) {
        matrix = new int[n][m];
    }

    public int get(int i, int j) {
        if (!isValid(i, j)) {
            return 0;
        }
        return matrix[i][j];
    }

    public void set(int i, int j, int value) {
        if (!isValid(i, j)) {
            return;
        }
        matrix[i][j] = value;
    }

    public boolean isValid(int i, int j) {
        return !(i < 0 || i >= matrix.length) && !(j < 0 || j >= matrix[i].length);
    }

    public int getWidth() {
        if (matrix.length > 0) {
            return matrix[0].length;
        }
        return 0;
    }

    public int getHeight() {
        return matrix.length;
    }

    public boolean fillMe(int[][] matrix) {
        if (matrix.length != getHeight()) {
            return false;
        }
        if (matrix[0].length != getWidth()) {
            return false;
        }
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                set(i, j, matrix[i][j]);
            }
        }
        return true;
    }

    public IntMatrix minor(int i, int j) {
        if (!isValid(i, j)) {
            return null;
        }
        IntMatrix minorIntMatrix = new IntMatrix(getHeight() - 1, getWidth() - 1);
        for (int im = 0; im < getHeight(); im++) {
            for (int jm = 0; jm < getWidth(); jm++) {
                if (im < i && jm < j) {
                    minorIntMatrix.set(im, jm, get(im, jm));
                }
                if (im < i && jm > j) {
                    minorIntMatrix.set(im, jm - 1, get(im, jm));
                }
                if (im > i && jm < j) {
                    minorIntMatrix.set(im - 1, jm, get(im, jm));
                }
                if (im > i && jm > j) {
                    minorIntMatrix.set(im - 1, jm - 1, get(im, jm));
                }
            }
        }
        return minorIntMatrix;
    }

    public int det() {
        if (getWidth() == 1 && getHeight() == 1) {
            return get(0, 0);
        }
        int det = 0;
        //BY ROW
        for (int j = 0; j < getWidth(); j++) {
            IntMatrix minor = minor(0, j);
            if (minor == null) {
                return 0;
            }
            int minorDet = minor.det();
            int element = get(0, j) * sign(0, j) * minorDet;
            det += element;
        }
        return det;
    }

    public IntMatrix matrixOfMinors() {
        if (getWidth() == 0 || getHeight() == 0) {
            return null;
        }
        IntMatrix matrixOfMinors = new IntMatrix(getHeight(), getWidth());
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                matrixOfMinors.set(i, j, sign(i, j) * minor(i, j).det());
            }
        }
        return matrixOfMinors;
    }

    private int sign(int i, int j) {
        return ((i + j + 2) % 2 == 0) ? 1 : -1;
    }

    @Override
    public String toString() {
        return "IntMatrix{" +
                "matrix=" + Arrays.deepToString(matrix) +
                '}';
    }
}
