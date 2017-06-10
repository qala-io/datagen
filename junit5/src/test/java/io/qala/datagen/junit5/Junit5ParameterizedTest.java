package io.qala.datagen.junit5;

import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;
@SuppressWarnings("unused"/*some tests just check that the method param doesn't break anything but don't use it*/)
class Junit5ParameterizedTest {
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
        void ignoresName_if2ndParamIsNotPresent(int param) {
        }

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
        void ignoresName_if2ndParamIsNotPresent(int param) {
        }

        @RandomInt(name = "blah") @RandomInt(name = "blah")
        void passesName_asSecondParam(int param, String name) {
            assertEquals("blah", name);
        }

        @RandomInt(name = "name") @RandomInt
        void nameIsEmpty_byDefault(int value, String name) {
            assertTrue(name.equals("name") || name.isEmpty());
            assertChangedFromLastTime(name);
        }
    }

    @Nested class AlphanumericTest {
        @Alphanumeric void generatesAlphanumeric_from1_to100_byDefault(String value) {
            assertTrue(value.trim().length() >= 1);
            assertTrue(value.length() <= 100);
        }

        @Alphanumeric(name = "name") void setsNameAsArgumentIf2ndParameterExists(String value, String name) {
            assertEquals("name", name);
        }
        @Alphanumeric(name = "name") void ignoresName_if2ndParamIsAbsent(String value) {}

        @Alphanumeric(length = 29) void generatesFixedLengthAlphanumeric_ifLengthIsSet(String value) {
            assertEquals(29, value.length());
        }
        @Alphanumeric(min = 2, max = 3) void lengthIsTakenFrom_minMaxParams_ifLengthIsAbsent(String value) {
            assertTrue(value.length() >= 2 && value.length() <= 3);
        }
    }

    @Nested class AlphanumericsTest {
        @Alphanumeric(length = 1)
        @Alphanumeric(min = 2, max = 29)
        @Alphanumeric(length = 30)
        void canGenerateMultipleAlphanumerics(String value) {
            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertChangedFromLastTime(value);
        }

        @Alphanumeric(length = 1, name = "min boundary")
        @Alphanumeric(min = 2, max = 29, name = "middle value")
        @Alphanumeric(length = 30, name = "max boundary")
        void ignoresCaseName_ifItIsNotPassed(String value) {
            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertChangedFromLastTime(value);
        }

        @Alphanumeric(min = 2, max = 3, name = "name1")
        @Alphanumeric(length = 1, name = "name2")
        void passesName_if2ndParam_isPresent(String value, String name) {
            assertTrue(name.startsWith("name"));
            assertChangedFromLastTime(name);

            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertChangedFromLastTime(value);
        }
        @Alphanumeric(min = 2, max = 3, name = "name")
        @Alphanumeric(length = 1)
        void nameIsEmpty_byDefault(String value, String name) {
            assertTrue(name.equals("name") || name.isEmpty());
            assertChangedFromLastTime(name);
        }
    }

    private static void assertChangedFromLastTime(Object newValue) {
        assertNotEquals(PREV_VALUE, newValue);
        PREV_VALUE = newValue;
    }

    // Is used to check if value changed since last random generation.
    // Otherwise hardcoded values could've been returned and most of the tests would pass
    private static Object PREV_VALUE;
}
