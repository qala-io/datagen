package io.qala.datagen.examples.junit5;

import io.qala.datagen.junit5.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JUnit5ExampleTest {
    @Alphanumeric(length = 1, name = "min boundary")
    @Alphanumeric(min = 2, max = 29, name = "middle value")
    @Alphanumeric(length = 30, name = "max boundary")
    @English(max=30)
    @Unicode(max=30)
    @Numeric(max=30)
    void eachAnnotationInvokesTheTestOnceAndPassesParameters(String value, String name) {
        assertTrue(value.length() >= 1 && value.length() <= 31, "Failed case: " + name);
    }

    @RandomInt(min = 1, name = "greater than zero")
    @RandomInt(max = -1, name = "less than zero")
    void zeroInt_isNotPassed(int param, String name) {
        assertNotEquals(0, param, "Failed case: " + name);
    }

    @RandomLong(min = 1, name = "greater than zero")
    @RandomLong(max = -1, name = "less than zero")
    void zeroLong_isNotPassed(long value, String name) {
        assertNotEquals(0, value, "Failed case: " + name);
    }

    @RandomDouble(min = 1, name = "greater than zero")
    @RandomDouble(max = -1, name = "less than zero")
    void zeroLong_isNotPassed(double value, String name) {
        assertFalse(value > -1 && value < 1, "Failed case: " + name);
    }
}
