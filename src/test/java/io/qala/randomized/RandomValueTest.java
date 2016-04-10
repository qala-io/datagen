package io.qala.randomized;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import java.util.Random;

import static io.qala.randomized.RandomValue.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.gen5.api.Assertions.expectThrows;

@RunWith(JUnit5.class)
@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class RandomValueTest {
    @Nested @DisplayName("Integer Generator") class Integers {
        @Test void returnsPositiveIntegers() {
            assertThat(upTo(Integer.MAX_VALUE).integer(), greaterThan(0));
        }

        @Test void returnsNegativeIntegers() {
            assertThat(between(Integer.MIN_VALUE, -1).integer(), lessThan(0));
        }

        @Test void returnsSameNumberIfBoundariesEqual() {
            int boundary = new Random().nextInt();
            assertEquals(boundary, between(boundary, boundary).integer());
        }
        @Test void returnsAnyInteger() {
            assertNotNull(anyInteger());
        }

        @Test void throwsIfMaxBoundaryGreaterThanInteger() {
            expectThrows(NumberOutOfBoundaryException.class, () -> upTo(GREATER_THAN_MAX_INT).integer());
        }

        @Test void throwsIfMinBoundaryLessThanInteger() {
            expectThrows(NumberOutOfBoundaryException.class, () -> between(LESS_THAN_INT_MIN, LESS_THAN_INT_MIN).integer());
        }
    }

    @Nested @DisplayName("String Generator") class Strings {
        @Test void returnsStringBetweenBoundaries() {
            assertThat(upTo(100).alphanumeric().length(), lessThanOrEqualTo(100));
        }
        @Test void returnsStringWithExactLength() {
            assertThat(length(100).alphanumeric().length(), equalTo(100));
        }
        @Test void throwsIfMinBoundaryIsNegative() {
            expectThrows(NumberOutOfBoundaryException.class, () -> between(-1, 10).alphanumeric());
        }
    }

    private static final long
            LESS_THAN_INT_MIN = ((long) Integer.MIN_VALUE) - 1,
            GREATER_THAN_MAX_INT = ((long) Integer.MAX_VALUE) + 1;
}