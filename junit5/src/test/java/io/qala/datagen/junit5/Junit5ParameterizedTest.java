package io.qala.datagen.junit5;

import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

public class Junit5ParameterizedTest {
    @Nested class RandomIntTest {
        @RandomInt(min = 1, max = 10)
        void generatesIntInBoundaries(int param) {
            assertTrue(param <= 10);
            assertTrue(param >= 1);
        }

        @RandomInt @RandomInt
        void generatesAnyNumberByDefault(int param) {
            assertChangedFromLastTime(param);
        }

        @RandomInt(name = "zero")
        void ignoresName_if2ndParamIsNotPresent(int param) {}

        @RandomInt(name = "blah")
        void passesName_asSecondParam(int param, String name) {
            assertEquals("blah", name);
        }
    }

    @Nested class RandomIntsTest {
        @RandomInt(min = 1, name = "greater than zero")
        @RandomInt(max = -1, name = "less than zero")
        void canGenerateMultipleInts(int param) {
            assertNotEquals(0, param);
            assertChangedFromLastTime(param);
        }
        @RandomInt(name = "zero") @RandomInt
        void ignoresName_if2ndParamIsNotPresent(int param) {}

        @RandomInt(name = "blah") @RandomInt(name = "blah")
        void passesName_asSecondParam(int param, String name) {
            assertEquals("blah", name);
        }
    }


    @Alphanumeric void generatesAlphanumeric_from1_to100_byDefault(String value) {
        assertTrue(value.trim().length() >= 1);
        assertTrue(value.length() <= 100);
    }

    @Alphanumeric(name = "name") void setsNameAsArgumentIf2ndParameterExists(String value, String name) {
        assertEquals("name", name);
    }

    @Alphanumeric(length = 29) void generatesFixedLengthAlphanumeric_ifLengthIsSet(String value) {
        assertEquals(29, value.length());
    }

    @Alphanumeric(length = 1, name = "min boundary")
    @Alphanumeric(min = 2, max = 29, name = "middle value")
    @Alphanumeric(length = 30, name = "max boundary")
    void canGenerateMultipleAlphanumerics(String value, String caseName) {
        assertTrue(value.length() >= 1 && value.length() <= 31, "Failed: " + caseName);
        assertChangedFromLastTime(value);
    }

    @Alphanumeric(length = 1, name = "min boundary")
    @Alphanumeric(min = 2, max = 29, name = "middle value")
    @Alphanumeric(length = 30, name = "max boundary")
    void ignoresCaseName_ifItIsNotPassed(String value) {
        assertTrue(value.length() >= 1 && value.length() <= 31);
        assertChangedFromLastTime(value);
    }

    private static void assertChangedFromLastTime(Object newValue) {
        assertNotEquals(PREV_VALUE, newValue);
        PREV_VALUE = newValue;
    }

    // Is used to check if value changed since last random generation.
    // Otherwise hardcoded values could've been returned and most of the tests would pass
    private static Object PREV_VALUE;
}
