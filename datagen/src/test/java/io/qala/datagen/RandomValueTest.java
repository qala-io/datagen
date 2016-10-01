package io.qala.datagen;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static io.qala.datagen.ContainsNonAlphanumericsMatcher.containsNonAlphanumerics;
import static io.qala.datagen.ContainsOneOfMatcher.containsOneOf;
import static io.qala.datagen.ContainsOnlyMatcher.containsOnly;
import static io.qala.datagen.RandomShortApi.*;
import static io.qala.datagen.RandomValue.*;
import static io.qala.datagen.StringModifier.Impls.*;
import static io.qala.datagen.StringModifier.Impls.oneOf;
import static io.qala.datagen.Vocabulary.specialSymbols;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.junit.gen5.api.Assertions.assertThrows;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.gen5.api.Assertions.expectThrows;

@RunWith(JUnit5.class)
@SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "unused"})
public class RandomValueTest {
    @Nested @DisplayName("Integer Generator") class Integers {
        @Test void returnsPositiveIntegers() {
            assertThat(upTo(Integer.MAX_VALUE).integer(), greaterThan(0));
            assertThat(positiveInteger(), greaterThan(0));
        }
        @Test void returnsNegativeIntegers() {
            assertThat(between(Integer.MIN_VALUE, -1).integer(), lessThan(0));
            assertThat(integer(Integer.MIN_VALUE, -1), lessThan(0));
        }
        @Test void returnsSameNumberIfBoundariesEqual() {
            int boundary = new Random().nextInt();
            assertEquals(boundary, between(boundary, boundary).integer());
            assertEquals(boundary, integer(boundary, boundary));
        }
        @Test void returnsAnyInteger() {
            assertNotNull(integer());
        }

        @Test void throwsIfMaxBoundaryGreaterThanInteger() {
            expectThrows(NumberOutOfBoundaryException.class, () -> upTo(GREATER_THAN_MAX_INT).integer());
        }
        @Test void throwsIfMinBoundaryLessThanInteger() {
            expectThrows(NumberOutOfBoundaryException.class, () -> between(LESS_THAN_INT_MIN, LESS_THAN_INT_MIN).integer());
        }
    }

    @Nested @DisplayName("Long Generator") class Longs {
        @Test void returnsPositiveLong() {
            assertThat(upTo(Long.MAX_VALUE).Long(), greaterThan(0L));
            assertThat(positiveLong(), greaterThan(0L));
        }
        @Test void returnsNegativeLongs() {
            assertThat(Long(Long.MIN_VALUE, -1), lessThan(0L));
            assertThat(negativeLong(), lessThan(0L));
        }
        @Test void returnsSameNumberIfBoundariesEqual() {
            int boundary = new Random().nextInt();
            assertEquals(boundary, between(boundary, boundary).Long());
            assertEquals(boundary, Long(boundary, boundary));
        }
        @Test void returnsLongs_thatAreGreaterThan0_sometimes() {
            for(int i = 0; i < 50; i++) if(Long() > 0) return;
            fail("Random Long should've returned a positive number at least once");
        }
        @Test void returnsLong_thatAreLessThan0_sometimes() {
            for(int i = 0; i < 50; i++) if(Long() < 0) return;
            fail("Random Long should've returned a negative number at least once");
        }
    }

    @Nested @DisplayName("Double Generator") class Doubles {
        @Test void returnsDouble_betweenBoundaries() {
            double aDouble = Double(-100, 100);
            assertThat(aDouble, greaterThan(-100.));
            assertThat(aDouble, lessThan(100.));
        }
        @Test void returnsPositiveDoubles() {
            assertThat(Double(Double.MAX_VALUE), greaterThan(0.0));
            assertThat(positiveDouble(), greaterThan(0.0));
        }
        @Test void returnsNegativeDoubles() {
            assertThat(Double(Long.MIN_VALUE, 0.0), lessThan(0.0));
            assertThat(negativeDouble(), lessThan(0.0));
        }
        @Test void returnsDouble_thatAreGreaterThan0_sometimes() {
            for(int i = 0; i < 50; i++) if(Double() > 0) return;
            fail("Random Double should've returned a positive number at least once");
        }
        @Test void returnsDouble_thatAreLessThan0_sometimes() {
            for(int i = 0; i < 50; i++) if(Double() < 0) return;
            fail("Random Double should've returned a negative number at least once");
        }
        @Test void returnsDoubleGreaterThanTheSpecifiedOne() {
            double original = Long.MAX_VALUE - 10000;
            assertThat(original, lessThan(greaterDouble(original)));
        }

        @Test void throwsIfBoundariesEqual() {
            long boundary = Long();
            expectThrows(IllegalArgumentException.class, () -> Double(boundary, boundary));
        }
        @Test void throwsIfLowerIsGreaterThanUpper() {
            expectThrows(IllegalArgumentException.class, () -> Double(1, 0));
        }
    }

    @Nested @DisplayName("String Generator") class Strings {
        String[] allMethods = {"alphanumeric", "numeric", "unicode", "english", "specialSymbols"};
        @Test void returnsStringUpToMaxBoundary() {
            Assert.eachStringUpToMax(integer(1000), allMethods);
        }
        @Test void returnsStringBetweenBoundaries() {
            int min = integer(1000), max = min + integer(1000);
            Assert.eachStringBetweenBoundaries(min, max, allMethods);
            Assert.eachStringGeneratedByShortApiBetweenBoundaries(min, max, allMethods);
        }
        @Test void returnsStringWithExactLength() {
            Assert.eachStringExactlyOfRequiredLength(integer(5000), allMethods);
            Assert.eachStringGeneratedByShortApiExactlyOfRequiredLength(integer(5000), allMethods);
        }
        @Test void returnsEmptyStringIfLengthIsSetTo0() {
            Assert.eachStringExactlyOfRequiredLength(0, allMethods);
            Assert.eachStringGeneratedByShortApiExactlyOfRequiredLength(0, allMethods);
        }
        @Test void returnsNumbersIfNumeric() {
            assertThat(length(1000).numeric(), containsString("1"));
            assertThat(numeric(1000), containsString("1"));
        }
        @Test void doesNotReturnsNumbersIfEnglishRequested() {
            assertThat(length(100).english(), not(containsString("1")));
            assertThat(english(100), not(containsString("1")));
        }
        @Test void doesNotReturnWeirdUnicodesIfEnglishRequested() {
            assertThat(length(100).english(), not(containsNonAlphanumerics()));
            assertThat(english(100), not(containsNonAlphanumerics()));
        }

        @Test void createsUnicodeStringThatContainsNonAlphanumerics() {
            assertThat(length(1000).unicode(), containsNonAlphanumerics());
            assertThat(unicode(0, 1000), containsNonAlphanumerics());
        }

        @Test void createsStringWithSpecialSymbols() {
            assertThat(length(1000).specialSymbols(), containsString(","));
            assertThat(RandomShortApi.specialSymbols(1000), containsString(","));
        }

        @Test void createsStringFromGivenVocabulary() {
            assertThat(length(100).string('A', 'b', ' '), containsOnly('A', 'b', ' '));
            assertThat(length(100).string("AB "), containsOnly('A', 'B', ' '));
        }

        @Test void throwsIfVocabularyIsEmpty() {
            assertThrows(IllegalArgumentException.class, () -> length(1000).string());
            assertThrows(IllegalArgumentException.class, () -> length(10).string(""));
        }

        @Test void addsSpecialSymbolsViaStringModifiers() {
            assertThat(length(100).with(specialSymbol()).english(), containsOneOf(specialSymbols()));
        }
        @Test void doesNotModifyLengthIfAddsSpecialSymbol() {
            assertThat(length(100).with(specialSymbol()).english().length(), equalTo(100));
        }
        @Test void doesNotModifyLengthIfAddsMultipleChars() {
            assertThat(length(100).with(multipleOf("!!_#")).english().length(), equalTo(100));
        }
        @SuppressWarnings("unchecked")
        @Test void addsSymbolsOccasionally() {
            List<String> alphanumerics = length(5).with(occasional("!#")).alphanumerics(500);
            assertThat(alphanumerics, hasItems(containsString("!")));
            assertThat(alphanumerics, hasItems(containsString("#")));
            assertThat(alphanumerics, hasItems(not(containsString("!"))));
        }
        @Test void addsOneOfPassedSymbols() {
            assertThat(length(100).with(oneOf(",")).english(), containsString(","));
        }
        @Test void addsSpacesViaStringModifier() {
            assertThat(length(100).with(spaces()).numeric(), containsString(" "));
        }
        @Test void addsPrefixAtTheBeginning() {
            assertThat(length(10).with(prefix("BLAH")).numeric(), startsWith("BLAH"));
        }
        @Test void addsPrefixAtTheBeginningInBatchMode() {
            assertThat(length(10).with(prefix("BLAH")).alphanumerics(), everyItem(startsWith("BLAH")));
        }
        @Test void addsSuffixAtTheEnd() {
            assertThat(length(10).with(suffix("BLAH")).numeric(), endsWith("BLAH"));
        }
        @Test void addsSuffixAtTheEndInBatchMode() {
            assertThat(length(10).with(suffix("BLAH")).alphanumerics(), everyItem(endsWith("BLAH")));
        }
        @Test void addsSpacesAtTheBeginning() {
            assertThat(length(10).with(spaceLeft()).numeric(), startsWith(" "));
            assertThat(length(10).with(spacesLeft(2)).numeric(), startsWith("  "));
            assertThat(length(10).with(spacesLeft(2)).numerics(), everyItem(startsWith("  ")));
        }
        @Test void addsSpacesAtTheEnd() {
            assertThat(length(10).with(spaceRight()).english(), endsWith(" "));
            assertThat(length(10).with(spacesRight(2)).alphanumeric(), endsWith("  "));
            assertThat(length(10).with(spacesRight(2)).alphanumerics(), everyItem(endsWith("  ")));
        }
        @Test void doesNotDamageRestOfStringIfSpacesAddedAtTheEnd() {
            assertThat(length(10).with(spacesRight(2)).alphanumeric().substring(0, 8), not(containsString(" ")));
        }

        @Test void createsMultipleStringsInBatchMode() {
            List<String> alphanumerics = upTo(10).alphanumerics(5);
            assertThat(alphanumerics.size(), equalTo(5));
        }
        @Test void createsUpTo100StringsInBatchMode() {
            List<String> alphanumerics = upTo(10).alphanumerics();
            assertThat(alphanumerics.size(), greaterThanOrEqualTo(1));
            assertThat(alphanumerics.size(), lessThanOrEqualTo(100));
        }
        @Test void appliesMultipleModifiersInBatchMode() {
            List<String> result = between(5, 15).with(prefix("blah"), spaceRight(), spaceLeft()).alphanumerics();
            assertThat(result, everyItem(allOf(startsWith(" lah"), endsWith(" "))));
        }

        @Test void throwsIfMinBoundaryIsNegative() {
            expectThrows(NumberOutOfBoundaryException.class, () -> between(-1, 10).alphanumeric());
        }
    }
    @Nested @DisplayName("Date Generator") class Dates {
        @Test void returnsDateUpToMaxBoundary() {
            assertThat(upTo(new Date()).date(), lessThanOrEqualTo(new Date()));
        }
        @Test void startsDatesFrom1970_ifUpToUsed() {
            assertThat(upTo(new Date()).date(), greaterThanOrEqualTo(new Date(0)));
        }
        @Test void createsDatesBetweenBoundaries() {
            Date from = new Date();
            Date to = new Date();
            assertThat(between(from, to).date(), greaterThanOrEqualTo(from));
            assertThat(between(from, to).date(), lessThanOrEqualTo(to));
        }
    }

    @Nested @DisplayName("Boolean Generator") class Booleans {
        @Test void canReturnTrueAndFalse() {
            boolean[] bools = bools(500);
            assertArrayContains(bools, true);
            assertArrayContains(bools, false);
        }
        @Test void canReturnNulls() {
            for(int i = 0; i < 1000; i++) if(nullableBool() == null) return;
            fail("Nullable Boolean should've returned null at least once");
        }
        @Test void canReturnTrue() {
            for(int i = 0; i < 1000; i++) if(nullableBool() == Boolean.TRUE) return;
            fail("Nullable Boolean should've returned True at least once");
        }
        @Test void canReturnFalse() {
            for(int i = 0; i < 1000; i++) if(nullableBool() == Boolean.FALSE) return;
            fail("Nullable Boolean should've returned False at least once");
        }

        @Test void alwaysReturnsTrueIfProbabilityOfTrueIs1() {
            assertTrue(weighedTrue(1));
        }
        @Test void alwaysReturnsFalseIfProbabilityOfTrueIs0() {
            assertFalse(weighedTrue(0));
        }
        @Test void weighedTrueCanReturnsTrue_sometimes() {
            for(int i = 0; i < 50; i++) if(weighedTrue(.5)) return;
            fail("weighedTrue() had to return True at least once, but it didn't");
        }
        @Test void weighedTrueCanReturnsFalse_sometimes() {
            for(int i = 0; i < 50; i++) if(!weighedTrue(.5)) return;
            fail("weighedTrue() had to return False at least once, but it didn't");
        }
        private void assertArrayContains(boolean[] array, boolean element) {
            for (boolean anArray : array) {
                if (anArray == element) return;
            }
            fail("Couldn't find element [" + element + "] in the array");
        }
    }


    private static class Assert {
        static void eachStringBetweenBoundaries(int min, int max, String... methods) {
            for (String method : methods) {
                RandomValue value = between(min, max);
                String generated = invokeAndGetString(value, method);
                String msg = "Method [" + method + "] returned: " + generated;
                assertThat(msg, generated.length(), lessThanOrEqualTo(max));
                assertThat(msg, generated.length(), greaterThanOrEqualTo(min));
            }
        }

        static void eachStringGeneratedByShortApiBetweenBoundaries(int min, int max, String... methods) {
            for (String method : methods) {
                String generated = invokeShortApiAndGetString(method, min, max);
                String msg = "Static method [" + method + "] returned: " + generated;
                assertThat(msg, generated.length(), lessThanOrEqualTo(max));
                assertThat(msg, generated.length(), greaterThanOrEqualTo(min));
            }
        }

        private static void eachStringUpToMax(int max, String... methods) {
            for (String method : methods) {
                RandomValue value = upTo(max);
                String generated = invokeAndGetString(value, method);

                String msg = "Method [" + method + "] returned: " + generated;
                assertThat(msg, generated.length(), lessThanOrEqualTo(max));
                assertThat(msg, generated.length(), greaterThanOrEqualTo(0));
            }
        }

        static void eachStringExactlyOfRequiredLength(int exactLength, String... methods) {
            for (String method : methods) {
                RandomValue value = length(exactLength);
                String generated = invokeAndGetString(value, method);

                String msg = "Static method [" + method + "] returned: " + generated;
                assertThat(msg, generated.length(), lessThanOrEqualTo(exactLength));
                assertThat(msg, generated.length(), greaterThanOrEqualTo(0));
            }
        }
        static void eachStringGeneratedByShortApiExactlyOfRequiredLength(int exactLength, String... methods) {
            for (String method : methods) {
                String generated = invokeShortApiAndGetString(method, exactLength);
                String msg = "Static method [" + method + "] returned: " + generated;
                assertThat(msg, generated.length(), lessThanOrEqualTo(exactLength));
                assertThat(msg, generated.length(), greaterThanOrEqualTo(0));
            }
        }

        private static String invokeAndGetString(RandomValue value, String method) {
            try {
                Method toInvoke = RandomValue.class.getDeclaredMethod(method);
                return (String) toInvoke.invoke(value);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException(e);
            }
        }

        private static String invokeShortApiAndGetString(String method, Object... params) {
            Class<?>[] paramClasses = new Class[params.length];
            for(int i = 0; i < params.length; i++) {
                if(params[i].getClass() == Integer.class) paramClasses[i] = int.class;
                else throw new IllegalArgumentException("Wrong type of the parameter found - " +
                        "no such randomized methods that can accept" + params[i].getClass());
            }
            try {
                Method toInvoke = RandomShortApi.class.getDeclaredMethod(method, paramClasses);
                return (String) toInvoke.invoke(null, params);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static final long
            LESS_THAN_INT_MIN = ((long) Integer.MIN_VALUE) - 1,
            GREATER_THAN_MAX_INT = ((long) Integer.MAX_VALUE) + 1;
}