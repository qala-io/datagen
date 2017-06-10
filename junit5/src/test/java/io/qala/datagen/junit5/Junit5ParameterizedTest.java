package io.qala.datagen.junit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static io.qala.datagen.RandomShortApi.english;
import static io.qala.datagen.RandomShortApi.numeric;
import static org.junit.jupiter.api.Assertions.*;
@SuppressWarnings("unused"/*some tests just check that the method param doesn't break anything but don't use it*/)
@DisplayName("JUnit5 Parameterized")
class Junit5ParameterizedTest {
    @Nested class IntGenerator {
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
    @Nested class IntsGenerator {
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

        @RandomInt(min = 2, max = 10) @RandomInt(min = 2, max = 10)
        void nameIsGeneric_byDefault(int value, String name) {
            assertEquals("int from 2 to 10", name);
        }
    }

    @Nested class LongGenerator {
        @RandomLong(min = 1, max = 10)
        void generatesIntInBoundaries(long param) {
            assertTrue(param <= 10);
            assertTrue(param >= 1);
        }

        @RandomLong @RandomLong @SuppressWarnings("ConstantConditions")
        void generatesAnyNumberByDefault(long param) {
            assertTrue(param >= Long.MIN_VALUE);
            assertTrue(param <= Long.MAX_VALUE);
            assertChangedFromLastTime(param);
        }

        @RandomLong(name = "zero")
        void ignoresName_if2ndParamIsNotPresent(long param) {
        }

        @RandomLong(name = "blah")
        void passesName_asSecondParam(long param, String name) {
            assertEquals("blah", name);
        }
    }
    @Nested class LongsGenerator {
        @RandomLong(min = 1, name = "greater than zero")
        @RandomLong(max = -1, name = "less than zero")
        void canGenerateMultipleNumbers(long param) {
            assertNotEquals(0, param);
            assertChangedFromLastTime(param);
        }

        @RandomLong(name = "zero") @RandomLong
        void ignoresName_if2ndParamIsNotPresent(long param) {
        }

        @RandomLong(name = "blah") @RandomLong(name = "blah")
        void passesName_asSecondParam(long param, String name) {
            assertEquals("blah", name);
        }

        @RandomLong(min = 2, max = 10) @RandomLong(min = 2, max = 10)
        void nameIsGeneric_byDefault(long value, String name) {
            assertEquals("long from 2 to 10", name);
        }
    }

    @Nested class DoubleGenerator {
        @RandomDouble(min = 1, max = 10)
        void generatesIntInBoundaries(double param) {
            assertTrue(param <= 10);
            assertTrue(param >= 1);
        }

        @RandomDouble @RandomDouble @SuppressWarnings("ConstantConditions")
        void generatesAnyNumberByDefault(double param) {
            assertTrue(param >= Long.MIN_VALUE);
            assertTrue(param <= Long.MAX_VALUE);
            assertChangedFromLastTime(param);
        }

        @RandomDouble(name = "zero")
        void ignoresName_if2ndParamIsNotPresent(double param) {
        }

        @RandomDouble(name = "blah")
        void passesName_asSecondParam(double param, String name) {
            assertEquals("blah", name);
        }
    }
    @Nested class DoublesGenerator {
        @RandomDouble(min = 1, name = "greater than zero")
        @RandomDouble(max = -1, name = "less than zero")
        void canGenerateMultipleNumbers(double param) {
            assertNotEquals(0, param);
            assertChangedFromLastTime(param);
        }

        @RandomDouble(name = "zero") @RandomDouble
        void ignoresName_if2ndParamIsNotPresent(double param) {
        }

        @RandomDouble(name = "blah") @RandomDouble(name = "blah")
        void passesName_asSecondParam(double param, String name) {
            assertEquals("blah", name);
        }

        @RandomDouble(min = 2, max = 10) @RandomDouble(min = 2, max = 10)
        void nameIsGeneric_byDefault(double value, String name) {
            assertEquals("double from 2.0 to 10.0", name);
        }
    }

    @Nested class AlphanumericGenerator {
        @Alphanumeric void generatesStringWithCorrectSymbols(String value) {
            assertEquals(value.getBytes().length, value.length());
        }
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
    @Nested class AlphanumericsGenerator {
        @Alphanumeric @Alphanumeric(length = 10) void generatesStringWithCorrectSymbols(String value) {
            assertEquals(value.getBytes().length, value.length());
        }

        @Alphanumeric(length = 1)
        @Alphanumeric(min = 2, max = 29)
        @Alphanumeric(length = 30)
        void canGenerateMultipleAlphanumerics(String value) {
            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertChangedFromLastTime(value);
        }

        @Alphanumeric(min = 20, max = 29, name = "middle value")
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
        @Alphanumeric(min = 2, max = 3)
        @Alphanumeric(length = 1)
        void nameIsEmpty_byDefault(String value, String name) {
            assertEquals("latin letters and digits", name);
        }
    }

    @Nested class EnglishGenerator {
        @English void generatesString_from1_to100_byDefault(String value) {
            assertTrue(value.trim().length() >= 1);
            assertTrue(value.length() <= 100);
        }
        @English void generatesStringWithCorrectSymbols(String english) {
            assertFalse(english.contains(numeric(1)));
            assertFalse(english.contains(" "));
            assertEquals(english.length(), english.getBytes().length);
        }
        @English(length = 10) void generatesStringWithCorrectSymbols_whenLengthUsed(String english) {
            assertFalse(english.contains(numeric(1)));
            assertFalse(english.contains(" "));
            assertEquals(english.length(), english.getBytes().length);
        }

        @English(name = "name") void setsNameAsArgumentIf2ndParameterExists(String value, String name) {
            assertEquals("name", name);
        }
        @English(name = "name") void ignoresName_if2ndParamIsAbsent(String value) {}
        @English(min = 2, max = 3) void nameIsGeneric_byDefault(String value, String name) {
            assertEquals("latin letters", name);
        }

        @English(length = 29) void generatesFixedLengthAlphanumeric_ifLengthIsSet(String value) {
            assertEquals(29, value.length());
        }
        @English(min = 2, max = 3) void lengthIsTakenFrom_minMaxParams_ifLengthIsAbsent(String value) {
            assertTrue(value.length() >= 2 && value.length() <= 3);
        }
    }
    @Nested class EnglishesGenerator {
        @English @English(length = 20)
        void generatesStringWithCorrectSymbols(String english) {
            assertFalse(english.contains(numeric(1)));
            assertFalse(english.contains(" "));
            assertEquals(english.length(), english.getBytes().length);
        }

        @English(length = 1)
        @English(min = 2, max = 29)
        @English(length = 30)
        void canGenerateMultipleStrings(String value) {
            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertFalse(value.contains("1"));
            assertChangedFromLastTime(value);
        }

        @English(length = 1, name = "min boundary")
        @English(min = 2, max = 29, name = "middle value")
        @English(length = 30, name = "max boundary")
        void ignoresCaseName_ifItIsNotPassed(String value) {
            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertChangedFromLastTime(value);
        }

        @English(min = 2, max = 3, name = "name1")
        @English(length = 1, name = "name2")
        void passesName_if2ndParam_isPresent(String value, String name) {
            assertTrue(name.startsWith("name"));
            assertChangedFromLastTime(name);

            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertChangedFromLastTime(value);
        }
        @English(min = 2, max = 3)
        @English(length = 1)
        void nameIsGeneric_byDefault(String value, String name) {
            assertEquals("latin letters", name);
        }
    }

    @Nested class NumericGenerator {
        @Numeric void generatesString_from1_to100_byDefault(String value) {
            assertTrue(value.trim().length() >= 1);
            assertTrue(value.length() <= 100);
        }
        @Numeric(min = 90) void generatesStringWithCorrectSymbols(String s) {
            assertTrue(s.contains(numeric(1)));
            assertFalse(s.contains(" "));
            assertFalse(s.contains(english(1)));
            assertEquals(s.length(), s.getBytes().length);
        }
        @Numeric(length = 300) void generatesStringWithCorrectSymbols_whenLengthUsed(String s) {
            assertTrue(s.contains(numeric(1)));
            assertFalse(s.contains(" "));
            assertFalse(s.contains(english(1)));
            assertEquals(s.length(), s.getBytes().length);
        }

        @Numeric(name = "name") void setsNameAsArgumentIf2ndParameterExists(String value, String name) {
            assertEquals("name", name);
        }
        @Numeric(name = "name") void ignoresName_if2ndParamIsAbsent(String value) {}
        @Numeric(length = 1)
        void nameIsGeneric_byDefault(String value, String name) {
            assertEquals("digits", name);
        }

        @Numeric(length = 29) void generatesFixedLengthAlphanumeric_ifLengthIsSet(String value) {
            assertEquals(29, value.length());
        }
        @Numeric(min = 2, max = 3) void lengthIsTakenFrom_minMaxParams_ifLengthIsAbsent(String value) {
            assertTrue(value.length() >= 2 && value.length() <= 3);
        }
    }
    @Nested class NumericsGenerator {
        @Numeric(min = 150, max = 160) @Numeric(length = 200)
        void generatesStringWithCorrectSymbols(String s) {
            assertTrue(s.contains(numeric(1)));
            assertFalse(s.contains(" "));
            assertFalse(s.contains(english(1)));
            assertEquals(s.length(), s.getBytes().length);
        }

        @Numeric(length = 1)
        @Numeric(min = 2, max = 29)
        @Numeric(length = 30)
        void canGenerateMultipleStrings(String value) {
            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertChangedFromLastTime(value);
        }

        @Numeric(length = 1, name = "min boundary")
        @Numeric(min = 2, max = 29, name = "middle value")
        @Numeric(length = 30, name = "max boundary")
        void ignoresCaseName_ifItIsNotPassed(String value) {
            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertChangedFromLastTime(value);
        }

        @Numeric(min = 2, max = 3, name = "name1")
        @Numeric(length = 1, name = "name2")
        void passesName_if2ndParam_isPresent(String value, String name) {
            assertTrue(name.startsWith("name"));
            assertChangedFromLastTime(name);

            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertChangedFromLastTime(value);
        }
        @Numeric(min = 2, max = 3)
        @Numeric(length = 1)
        void nameIsGeneric_byDefault(String value, String name) {
            assertEquals("digits", name);
        }
    }

    @Nested class UnicodeGenerator {
        @Unicode void generatesString_from1_to100_byDefault(String value) {
            assertTrue(value.trim().length() >= 1);
            assertTrue(value.length() <= 100);
        }
        @Unicode(min = 90) void generatesStringWithCorrectSymbols(String string) {
            assertNotEquals(string.getBytes().length, string.length());
        }
        @Unicode(length = 100) void generatesStringWithCorrectSymbols_whenLengthUsed(String string) {
            assertNotEquals(string.getBytes().length, string.length());
        }

        @Unicode(name = "name") void setsNameAsArgumentIf2ndParameterExists(String value, String name) {
            assertEquals("name", name);
        }
        @Unicode(name = "name") void ignoresName_if2ndParamIsAbsent(String value) {}

        @Unicode(length = 29) void generatesFixedLengthAlphanumeric_ifLengthIsSet(String value) {
            assertEquals(29, value.length());
        }
        @Unicode(min = 2, max = 3) void lengthIsTakenFrom_minMaxParams_ifLengthIsAbsent(String value) {
            assertTrue(value.length() >= 2 && value.length() <= 3);
        }
    }
    @Nested class UnicodesGenerator {
        @Unicode(length = 100) @Unicode(min = 90)
        void generatesStringWithCorrectSymbols(String string) {
            assertNotEquals(string.getBytes().length, string.length());//most unicodes >1 byte
        }
        @Unicode(length = 1)
        @Unicode(min = 2, max = 29)
        @Unicode(length = 30)
        void canGenerateMultipleStrings(String value) {
            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertFalse(value.contains("1"));
            assertChangedFromLastTime(value);
        }

        @Unicode(length = 1, name = "min boundary")
        @Unicode(min = 2, max = 29, name = "middle value")
        @Unicode(length = 30, name = "max boundary")
        void ignoresCaseName_ifItIsNotPassed(String value) {
            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertChangedFromLastTime(value);
        }

        @Unicode(min = 2, max = 3, name = "name1")
        @Unicode(length = 1, name = "name2")
        void passesName_if2ndParam_isPresent(String value, String name) {
            assertTrue(name.startsWith("name"));
            assertChangedFromLastTime(name);

            assertTrue(value.length() >= 1 && value.length() <= 31);
            assertChangedFromLastTime(value);
        }
        @Unicode(min = 2, max = 3)
        @Unicode(length = 1)
        void nameIsGeneric_byDefault(String value, String name) {
            assertEquals("unicode symbols", name);
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
