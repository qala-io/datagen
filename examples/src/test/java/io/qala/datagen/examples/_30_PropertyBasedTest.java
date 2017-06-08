package io.qala.datagen.examples;

import org.junit.Test;

import static io.qala.datagen.RandomShortApi.integer;
import static org.junit.Assert.*;

/**
 * Source: <a href="http://fsharpforfunandprofit.com/posts/property-based-testing/">click</a>
 */
public class _30_PropertyBasedTest {
    @Test public void orderDoesNotMatter() {
        int a = integer(), b = integer();
        assertEquals(MyMath.sum(a, b), MyMath.sum(b, a));
    }
    @Test public void adding1twoTimesIsSameAsAdding2() {
        int a = integer();
        int result1 = MyMath.sum(a, 1);
        assertEquals(MyMath.sum(result1, 1), MyMath.sum(a, 2));
    }
    @Test public void addingZeroIsSameAsDoingNothing() {
        int a = integer();
        assertEquals(a, MyMath.sum(a, 0));
    }
}