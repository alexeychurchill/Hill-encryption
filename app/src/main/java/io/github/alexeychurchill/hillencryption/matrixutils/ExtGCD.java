package io.github.alexeychurchill.hillencryption.matrixutils;

/**
 * Extended GCD.
 */

public class ExtGCD {
    public static int extGCD(int a, int b, CoefficientPair pair) {
        if (a == 0) {
            pair.setX(0);
            pair.setY(1);
            return b;
        }
        CoefficientPair newPair = new CoefficientPair();
        int d = extGCD(b % a, a, newPair);
        pair.setX(newPair.getY() - (b /a) * newPair.getX());
        pair.setY(newPair.getX());
        return d;
    }

    public static class CoefficientPair {
        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
