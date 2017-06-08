package io.qala.datagen.examples;

import java.math.BigInteger;

class NaiveCombinations {
    static BigInteger combinations(int n, int k) {
        return factorial(n).divide(factorial(n - k).multiply(factorial(k)));
    }

    private static BigInteger factorial(long n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}
