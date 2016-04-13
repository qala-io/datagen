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
