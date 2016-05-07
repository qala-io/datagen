package io.qala.datagen;

import static io.qala.datagen.RandomElements.from;
import static io.qala.datagen.RandomValue.*;

public class RandomShortApi {
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

    public static Boolean nullableBool() {
        return from(Boolean.TRUE, Boolean.FALSE, null).sample();
    }
}
