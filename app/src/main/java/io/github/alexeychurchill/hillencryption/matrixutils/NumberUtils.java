package io.github.alexeychurchill.hillencryption.matrixutils;

/**
 * Number utils
 */

public class NumberUtils {
    public static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    public static boolean isInverseByModExists(int a, int m) {
        return gcd(a, m) <= 1;
    }

    public static int inverseByMod(int a, int m) {
        if (!isInverseByModExists(a, m)) {
            throw new IllegalArgumentException("Inverse to " + a + " not exists by " + m + "!");
        }
        int b = 1;
        while ((a * b) % m != 1) {
            b++;
        }
        return b;
    }

    public static int mod(int a, int m) {
        int mod = a % m;
        return (mod > 0) ? mod : m + mod;
    }
}
