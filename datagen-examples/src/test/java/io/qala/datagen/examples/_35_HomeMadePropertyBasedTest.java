package io.qala.datagen.examples;

import org.junit.Test;

import static io.qala.datagen.NotReleasedFeatures.greaterDouble;
import static io.qala.datagen.RandomShortApi.positiveDouble;
import static io.qala.datagen.examples.OverageCalculation.overage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class _35_HomeMadePropertyBasedTest {
    @Test public void overageExamples() {
        assertEquals(.3333, overage(9, 12), .0001);
        assertEquals(-.25, overage(12, 9), .0001);
    }

    @Test public void overageIsPositive_ifWeGotMoreThanAsked() {
        double wanted = positiveDouble();
        double got = greaterDouble(wanted);
        assertTrue(overage(wanted, got) >= 0);
    }

    @Test public void overageIsZero_ifWeGotExactlyEnough() {
        double value = positiveDouble();
        assertEquals(0, overage(value, value), .00001);
    }

    @Test public void overageIsNegative_ifWeGotLessThanWeAsked() {
        double wanted = positiveDouble();
        double got = greaterDouble(wanted);
        assertTrue(overage(got, wanted) <= 0);
    }

    @Test public void overageIsPlus100_ifWeGotTwiceMore() {
        double got = positiveDouble();
        double wanted = got * 2;
        assertEquals(1, overage(got, wanted), .00001);
    }

    @Test public void overageIsMinus50_ifWeGotTwiceLess() {
        double wanted = positiveDouble();
        double got = wanted * 2;
        assertEquals(-.5, overage(got, wanted), .00001);
    }
}