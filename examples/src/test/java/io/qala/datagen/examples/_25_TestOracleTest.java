package io.qala.datagen.examples;

import org.junit.Test;

import static io.qala.datagen.RandomShortApi.integer;
import static org.junit.Assert.assertEquals;

public class _25_TestOracleTest {
    @Test public void nOfCombinations_matchesSimpleImplementation() {
        int n = integer(1, 1000), k = integer(1, n);
        assertEquals(NaiveCombinations.combinations(n, k), MultiplicativeCombinations.combinations(n, k));
    }
}
