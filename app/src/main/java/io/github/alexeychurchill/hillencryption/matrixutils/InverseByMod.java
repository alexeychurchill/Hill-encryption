package io.github.alexeychurchill.hillencryption.matrixutils;

public class InverseByMod {
    public static boolean isExists(int a, int m) {
        ExtGCD.CoefficientPair coefficientPair = new ExtGCD.CoefficientPair();
        int g = ExtGCD.extGCD(a, m, coefficientPair);
        return g == 1;
    }

    public static int inverse(int a, int m) {
        ExtGCD.CoefficientPair coefficientPair = new ExtGCD.CoefficientPair();
        ExtGCD.extGCD(a, m, coefficientPair);
        return (coefficientPair.getX() % m + m) % m;
    }
}
