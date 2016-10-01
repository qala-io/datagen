package io.qala.datagen;

import io.qala.datagen.adaptors.CommonsMath4;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.qala.datagen.RandomElements.from;
import static io.qala.datagen.RandomValue.*;

/**
 * If you need a more flexible way of generating the values including different string modifiers like prefixes and
 * suffixes, use {@link RandomValue}.
 */
@SuppressWarnings({"WeakerAccess", "Convert2Diamond", "Convert2streamapi"})
public class RandomShortApi {
    private RandomShortApi() {}

    public static int integer(int max) {
        return upTo(max).integer();
    }
    public static int integer(int min, int max) {
        return between(min, max).integer();
    }
    public static int integer() {
        return between(Integer.MIN_VALUE, Integer.MAX_VALUE).integer();
    }
    public static int positiveInteger() {
        return upTo(Integer.MAX_VALUE).integer();
    }
    public static long Long() {
        return between(Long.MIN_VALUE, Long.MAX_VALUE).Long();
    }

    public static double Double() {
        return CommonsMath4.nextUniform(RANDOM, Long.MIN_VALUE, Long.MAX_VALUE, true);
    }

    /**
     * Creates double that's {@code >=} 0.
     *
     * @return double that's {@code >=} 0
     */
    public static double positiveDouble() {
        return Double(Long.MAX_VALUE);
    }

    public static double negativeDouble() {
        return Double(Long.MIN_VALUE, 0);
    }

    public static double Double(double max) {
        return CommonsMath4.nextUniform(RANDOM, 0, max, true);
    }

    /**
     * Returns double that's greater than what was specified.
     * @param from lower boundary
     * @return double that's greater than what was specified
     */
    static double greaterDouble(double from) {//todo: not released!
        return Double(from + Double.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * Returns random double.
     *
     * @param min min boundary (inclusive), cannot be equal to {@code max}
     * @param max max boundary (exclusive), cannot be equal to {@code min}
     * @return uniformly distributed random double between 2 boundaries
     */
    public static double Double(double min, double max) {
        return CommonsMath4.nextUniform(RANDOM, min, max, true);
    }

    /**
     * Long between 0 to {@link Long#MAX_VALUE} inclusive.
     *
     * @return between 0 to {@link Long#MAX_VALUE} inclusive.
     */
    public static long positiveLong() {
        return Long(0, Long.MAX_VALUE);
    }

    /**
     * Long between {@link Long#MIN_VALUE} to -1
     *
     * @return between {@link Long#MIN_VALUE} to -1
     */
    public static long negativeLong() {
        return Long(Long.MIN_VALUE, -1);
    }

    /**
     * @param min inclusive
     * @param max inclusive
     * @return a long from min boundary to max
     */
    public static long Long(long min, long max) {
        return between(min, max).Long();
    }
    public static String alphanumeric(int exactLength) {
        return length(exactLength).alphanumeric();
    }
    public static String alphanumeric(int min, int max) {
        return between(min, max).alphanumeric();
    }

    public static String numeric(int exactLength) {
        return length(exactLength).numeric();
    }
    public static String numeric(int min, int max) {
        return between(min, max).numeric();
    }

    public static String english(int exactLength) {
        return length(exactLength).english();
    }
    public static String english(int min, int max) {
        return between(min, max).english();
    }

    /**
     * Generates unicode string of variable length that includes characters from different languages, weird symbols
     * and <a href="https://docs.oracle.com/javase/tutorial/i18n/text/supplementaryChars.html">Supplementary Characters</a>
     * that are comprised of multiple chars.
     *
     * @param exactLength length of the string to be returned
     * @return unicode characters including different languages and weird symbols
     */
    public static String unicode(int exactLength) {
        return length(exactLength).unicode();
    }

    /**
     * Generates unicode string of variable length that includes characters from different languages, weird symbols
     * and <a href="https://docs.oracle.com/javase/tutorial/i18n/text/supplementaryChars.html">Supplementary Characters</a>
     * that are comprised of multiple chars.
     *
     * @param min min boundary of the string length
     * @param max max boundary of the string length
     * @return unicode characters including different languages and weird symbols
     */
    public static String unicode(int min, int max) {
        return between(min, max).unicode();
    }

    public static String specialSymbols(int exactLength) {
        return length(exactLength).specialSymbols();
    }
    public static String specialSymbols(int min, int max) {
        return between(min, max).specialSymbols();
    }

    /**
     * Returns an array of random booleans (true/false).
     *
     * @param n size of the resulting array
     * @return an array of random booleans
     */
    public static boolean[] bools(int n) {
        boolean[] result = new boolean[n];
        for (int i = 0; i < n; i++) {
            result[i] = bool();
        }
        return result;
    }

    public static boolean bool() {
        return RandomValue.RANDOM.nextBoolean();
    }

    /**
     * Returns true with the specified probability.
     *
     * @param probabilityOfTrue the probability that true is to be returned, must be between 0 and 1
     * @return true with the specified probability. Always returns true if 1 is passed and always false if 0 is passed.
     */
    @SuppressWarnings("SimplifiableIfStatement")
    public static boolean weighedTrue(double probabilityOfTrue) {
        if (probabilityOfTrue == 0.0) return false;
        if (probabilityOfTrue == 1.0) return true;
        return probabilityOfTrue >= RANDOM.nextDouble();
    }
    /**
     * Besides returning TRUE or FALSE it can also return {@code null}.
     *
     * @return TRUE, FALSE or {@code null}
     */
    public static Boolean nullableBool() {
        return from(Boolean.TRUE, Boolean.FALSE, null).sample();
    }

    /**
     * Returns random element from the collection.
     *
     * @param toSampleFrom retrieve random element from
     * @return a random element from the collection
     * @see RandomElements
     */
    public static <T> T sample(Collection<T> toSampleFrom) {
        return from(toSampleFrom).sample();
    }

    /**
     * Returns random element from the collection.
     *
     * @param toSampleFrom the population of the elements you'd like to get a random value from
     * @return a random element from the collection
     * @see RandomElements
     */
    @SafeVarargs public static <T> T sample(T... toSampleFrom) {
        return from(toSampleFrom).sample();
    }

    /**
     * Returns either null or the specified object.
     *
     * @param obj object to return in 50% of cases
     * @param <T> type of the specified object
     * @return null or the specified object with the 50/50 odds
     */
    public static <T> T nullOr(T obj) {
        return sample(obj, null);
    }

    /**
     * Returns either null or empty string with the 50/50 odds.
     *
     * @return either null or empty string
     */
    public static String nullOrEmpty() {
        return sample("", null);
    }

    /**
     * Returns either null or empty string or string with only spaces each with equal chance.
     *
     * @return either null or empty string or string with whitespaces only
     */
    public static String nullOrBlank() {
        return sample("", between(1, 100).string(' '), null);
    }

    /**
     * Returns either the specified string or null or empty string or string with only spaces each with equal chance.
     *
     * @return either the specified string or null or empty string or string with whitespaces only
     */
    public static String blankOr(String string) {
        return sample(nullOrBlank(), string);
    }

    /**
     * Returns a random element from the collection. Is used in case you have a collection and then couple of other
     * elements you want to sample from too, but you don't want to create a collection that includes all of them
     * combined.
     *
     * @param elements the main collection to sample from
     * @param others   other elements you'd like to include into population to sample from
     * @return a random element from all the listed elements/other elements
     * @see RandomElements
     */
    @SafeVarargs public static <T> T sample(Collection<T> elements, T... others) {
        return from(elements, others).sample();
    }

    /**
     * Returns multiple random elements from the specified collection.
     *
     * @param toSampleFrom the population of the elements you'd like to get a random value from
     * @return 0 or more elements of the specified collection, elements don't repeat
     */
    public static <T> List<T> sampleMultiple(Collection<T> toSampleFrom) {
        return sampleMultiple(integer(toSampleFrom.size()), toSampleFrom);
    }

    /**
     * Returns multiple random elements from the specified collection.
     *
     * @param toSampleFrom the population of the elements you'd like to get a random value from
     * @return a random element from the collection
     */
    public static <T> Set<T> sampleMultiple(Set<T> toSampleFrom) {
        return new HashSet<T>(sampleMultiple((Collection<T>) toSampleFrom));
    }

    /**
     * Returns multiple random elements from the specified collection.
     *
     * @param toSampleFrom the population of the elements you'd like to get a random value from
     * @param nToReturn    number of elements to be returned, must be smaller than the collection size
     * @return list of size {@code nToReturn} - contains random elements from the specified collection
     */
    public static <T> List<T> sampleMultiple(int nToReturn, Collection<T> toSampleFrom) {
        return from(toSampleFrom).sample(nToReturn);
    }

    /**
     * Returns random element from the collection.
     *
     * @param toSampleFrom the population of the elements you'd like to get a random value from
     * @param nToReturn    number of elements to be returned, must be smaller than the collection size
     * @return list of size {@code nToReturn} - contains random elements from the specified array
     */
    @SafeVarargs public static <T> List<T> sampleMultiple(int nToReturn, T... toSampleFrom) {
        return from(toSampleFrom).sample(nToReturn);
    }

    /**
     * Invokes one and only one of the specified functions. This is an API for Java8 Lambdas.
     *
     * @param functions functions to choose from for invocation
     */
    public static void callOneOf(Function... functions) {
        sample(functions).call();
    }

    /**
     * May invoke 0, 1 or more functions from the specified list.
     *
     * @param functions functions to choose from for invocation
     */
    public static void callNoneOrMore(Function... functions) {
        List<Function> toCall = sampleMultiple(integer(functions.length), functions);
        for (Function function : toCall) function.call();
    }

    /**
     * Invokes one or more of the specified functions. This is an API for Java8 Lambdas.
     *
     * @param functions functions to choose from for invocation
     */
    public static void callOneOrMore(Function... functions) {
        List<Function> toCall = sampleMultiple(integer(1, functions.length), functions);
        for (Function function : toCall) function.call();
    }

    /**
     * Invokes one and only one of the specified functions. This is an API for Java8 Lambdas.
     *
     * @param functions functions to choose from for invocation
     * @deprecated use callOneOf()
     */
    @Deprecated
    public static void oneOf(Function... functions) {
        callOneOf(functions);
    }

    /**
     * May invoke 0, 1 or more functions from the specified list.
     *
     * @param functions functions to choose from for invocation
     * @deprecated use callNoneOrMore()
     */
    @Deprecated
    public static void noneOrMore(Function... functions) {
        callNoneOrMore(functions);
    }

    /**
     * Invokes one or more of the specified functions. This is an API for Java8 Lambdas.
     *
     * @param functions functions to choose from for invocation
     * @deprecated use callOneOrMore()
     */
    @Deprecated
    public static void oneOrMore(Function... functions) {
        callOneOrMore(functions);
    }
}
