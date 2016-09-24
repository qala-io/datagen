package io.qala.datagen;

import io.qala.datagen.adaptors.CommonsLang3RandomStringUtils;
import io.qala.datagen.adaptors.CommonsMath4;
import io.qala.datagen.adaptors.JavaRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.qala.datagen.adaptors.CommonsLang3RandomStringUtils.random;

@SuppressWarnings({"WeakerAccess", "Convert2Diamond"})
public class RandomValue implements RandomString {
    static final JavaRandom RANDOM = new JavaRandom();
    private final List<StringModifier> modifiers = new CopyOnWriteArrayList<StringModifier>();
    private final Long min;
    private final Long max;

    RandomValue(Long min, Long max) {
        if (max < min) throw new IllegalArgumentException("Min [" + min + "] cannot be larger than max [" + max + "]");
        this.min = min;
        this.max = max;
    }

    public static RandomValue between(long from, long to) {
        return new RandomValue(from, to);
    }
    public static RandomValue between(Date from, Date to) {
        return new RandomValue(from.getTime(), to.getTime());
    }

    public static RandomValue upTo(long to) {
        return new RandomValue(0L, to);
    }
    public static RandomValue upTo(Date to) {
        return new RandomValue(0L, to.getTime());
    }
    public static RandomValue length(long length) {
        return new RandomValue(length, length);
    }

    public Date date() {
        return new Date(Long());
    }

    public RandomString with(StringModifier ... modifiers) {
        this.modifiers.addAll(Arrays.asList(modifiers));
        return this;
    }

    @Override public String alphanumeric() {
        throwIfLowerBoundaryIsNegative();
        return applyStringModifiers(CommonsLang3RandomStringUtils.randomAlphanumeric(integer()));
    }
    @Override public List<String> alphanumerics() {
        return alphanumerics(between(1, 100).integer());
    }
    @Override public List<String> alphanumerics(int nOfElements) {
        throwIfLowerBoundaryIsNegative();
        List<String> result = new ArrayList<String>(nOfElements);
        for(int i = 0; i < nOfElements; i++) {
            result.add(CommonsLang3RandomStringUtils.randomAlphanumeric(integer()));
        }
        return applyStringModifiers(result);
    }
    @Override public List<String> alphanumerics(int minNOfElements, int maxNOfElements) {
        throwIfLowerBoundaryIsNegative();
        int n = between(minNOfElements, maxNOfElements).integer();
        return alphanumerics(n);
    }
    @Override public String numeric() {
        throwIfLowerBoundaryIsNegative();
        return applyStringModifiers(CommonsLang3RandomStringUtils.randomNumeric(integer()));
    }

    @Override public List<String> numerics() {
        return numerics(between(1, 100).integer());
    }
    @Override public List<String> numerics(int nOfElements) {
        throwIfLowerBoundaryIsNegative();
        List<String> result = new ArrayList<String>(nOfElements);
        for(int i = 0; i < nOfElements; i++) {
            result.add(CommonsLang3RandomStringUtils.randomNumeric(integer()));
        }
        return applyStringModifiers(result);
    }

    @Override public String english() {
        throwIfLowerBoundaryIsNegative();
        return applyStringModifiers(CommonsLang3RandomStringUtils.randomAlphabetic(integer()));
    }

    @Override public String specialSymbols() {
        throwIfLowerBoundaryIsNegative();
        return applyStringModifiers(random(integer(), Vocabulary.specialSymbols()));
    }

    @Override public String unicode() {
        throwIfLowerBoundaryIsNegative();
        return applyStringModifiers(CommonsLang3RandomStringUtils.random(integer()));
    }

    @Override public String string(char... vocabulary) {
        if (vocabulary == null || vocabulary.length == 0)
            throw new IllegalArgumentException("You cannot generate string from an empty vocabulary. Either pass " +
                    "the symbols the string is to be generated from, or use methods like alphanumerics(), unicode()," +
                    " etc.");
        throwIfLowerBoundaryIsNegative();
        return applyStringModifiers(random(integer(), vocabulary));
    }

    @Override public String string(String vocabulary) {
        return string(vocabulary.toCharArray());
    }

    private String applyStringModifiers(String value) {
        String result = value;
        for(StringModifier modifier: modifiers) {
            result = modifier.modify(result);
        }
        return result;
    }
    private List<String> applyStringModifiers(List<String> value) {
        List<String> result = value;
        for(StringModifier modifier: modifiers) {
            result = modifier.modify(result);
        }
        return result;
    }
    private void throwIfLowerBoundaryIsNegative() {
        if(min < 0) throw new NumberOutOfBoundaryException("String length cannot be less than 0:" + min);
    }
    /**
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
        return CommonsMath4.nextInt(RANDOM, maxInt(), minInt());
    }

    /**
     * Returns any Long from {@link Long#MIN_VALUE} to {@link Long#MAX_VALUE}.
     *
     * @return long from {@link Long#MIN_VALUE} to {@link Long#MAX_VALUE}
     */
    public long Long() {
        return CommonsMath4.nextLong(RANDOM, min, max);
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
