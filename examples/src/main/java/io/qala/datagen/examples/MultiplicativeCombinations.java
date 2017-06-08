package io.qala.datagen.examples;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

class MultiplicativeCombinations {
    static BigInteger combinations(int n, int k) {
        assert k <= n;
        BigDecimal result = BigDecimal.ONE;
        for (int i = 1; i <= k; i++) {
            result = result.multiply(BigDecimal.valueOf(n + 1 - i).divide(BigDecimal.valueOf(i), 300, RoundingMode.HALF_UP));
        }
        return result.setScale(0, RoundingMode.HALF_UP).toBigInteger();
    }
}
