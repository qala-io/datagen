package io.qala.datagen;

import io.qala.datagen.adaptors.CommonsLang3RandomStringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.qala.datagen.adaptors.CommonsLang3RandomStringUtils.random;

public class RandomValue {
    private static final Random RANDOM = new Random();
    private final List<StringModifier> modifiers = new CopyOnWriteArrayList<>();
    private final Long min;
    private final Long max;

    private RandomValue(Long min, Long max) {
        if (max < min) throw new IllegalArgumentException("Min [" + min + "] cannot be larger than max [" + max + "]");
        this.min = min;
        this.max = max;
    }

    public static RandomValue between(long from, long to) {
        return new RandomValue(from, to);
    }

    public static RandomValue upTo(long to) {
        return new RandomValue(0L, to);
    }
    public static RandomValue length(long length) {
        return new RandomValue(length, length);
    }

    public static int anyInteger() {
        return between(Integer.MIN_VALUE, Integer.MAX_VALUE).integer();
    }
    public static int positiveInteger() {
        return upTo(Integer.MAX_VALUE).integer();
    }

    public RandomValue with(StringModifier ... modifiers) {
        this.modifiers.addAll(Arrays.asList(modifiers));
        return this;
    }

    public String alphanumeric() {
        throwIfLowerBoundaryIsNegative();
        return applyStringModifiers(CommonsLang3RandomStringUtils.randomAlphanumeric(integer()));
    }
    public String numeric() {
        throwIfLowerBoundaryIsNegative();
        return applyStringModifiers(CommonsLang3RandomStringUtils.randomNumeric(integer()));
    }

    public String english() {
        throwIfLowerBoundaryIsNegative();
        return applyStringModifiers(CommonsLang3RandomStringUtils.randomAlphabetic(integer()));
    }
    public String specialSymbols() {
        throwIfLowerBoundaryIsNegative();
        return applyStringModifiers(random(integer(), Vocabulary.specialSymbols()));
    }

    private String applyStringModifiers(String value) {
        String result = value;
        for(StringModifier modifier: modifiers) {
            result = modifier.modify(result);
        }
        return result;
    }
    private void throwIfLowerBoundaryIsNegative() {
        if(min < 0) throw new NumberOutOfBoundaryException("String length cannot be less than 0:" + min);
    }
    /**
     * Mostly copied from Commons Math.
     * <p>
     * Generates a uniformly distributed random integer between {@code lower}
     * and {@code upper} (endpoints included).
     * <p> The generated integer will be random, but not cryptographically secure. </p>
     *
     * @return a random integer greater than or equal to {@code lower}
     * and less than or equal to {@code upper}
     * @throws NumberOutOfBoundaryException if previously specified boundaries are greater/smaller than integer
     *                                      boundaries
     */
    public int integer() {
        int upper = maxInt();
        int lower = minInt();
        int max = (upper - lower) + 1;
        if (max <= 0) {
            // The range is too wide to fit in a positive int (larger
            // than 2^31); as it covers more than half the integer range,
            // we use a simple rejection method.
            while (true) {
                int r = RANDOM.nextInt();
                if (r >= lower && r <= upper) return r;
            }
        } else {
            // We can shift the range and directly generate a positive int.
            return lower + RANDOM.nextInt(max);
        }
    }

    private int maxInt() {
        if (max > Integer.MAX_VALUE) {
            throw new NumberOutOfBoundaryException("The number was expected to be integer, but it's too large:" + max);
        }
        return max.intValue();
    }

    private int minInt() {
        if (min < Integer.MIN_VALUE) {
            throw new NumberOutOfBoundaryException("The number was expected to be integer, but it's too small:" + min);
        }
        return min.intValue();
    }
}
