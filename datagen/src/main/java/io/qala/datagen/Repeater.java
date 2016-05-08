package io.qala.datagen;

import java.util.ArrayList;
import java.util.List;

import static io.qala.datagen.RandomShortApi.integer;

/**
 * If the resulting string should have repeats, this class can do this. Note, that often there are separators between
 * the repeats which should be trimmed at the end - and this trimming is the default behaviour. To switch it off
 * use {@link #includeLastSymbol()}.
 */
public class Repeater {
    private final List<Object> toRepeat = new ArrayList<Object>();
    private int nOfLastSymbolsToRemove = 1;

    public static Repeater repeat(String toRepeat) {
        return new Repeater().string(toRepeat);
    }

    public static Repeater repeat(RandomValue randomValue, RandomString.Type type) {
        return new Repeater().random(randomValue, type);
    }

    public Repeater random(RandomValue randomValue, RandomString.Type type) {
        toRepeat.add(new RandomValueRecorder(randomValue, type));
        return this;
    }

    public Repeater string(String notRandomString) {
        toRepeat.add(notRandomString);
        return this;
    }

    public Repeater removeLastSymbols(int nOfSymbolsToRemoveFromTheEnd) {
        this.nOfLastSymbolsToRemove = nOfSymbolsToRemoveFromTheEnd;
        return this;
    }

    public Repeater includeLastSymbol() {
        this.nOfLastSymbolsToRemove = 0;
        return this;
    }

    public String times(int min, int max) {
        if(min < 0) throw new IllegalArgumentException("I cannot repeat string negative number of times");
        return times(integer(min, max));
    }

    public String times(int exactNumberOfTimes) {
        if(exactNumberOfTimes < 0) throw new IllegalArgumentException("I cannot repeat string negative number of times");
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < exactNumberOfTimes; i++) {
            for (Object next : toRepeat) {
                if (next instanceof String) {
                    str.append(next);
                } else {
                    RandomValueRecorder randomValue = (RandomValueRecorder) next;
                    str.append(randomValue.generate());
                }
            }
        }
        if(str.length() == 0) return "";
        if (nOfLastSymbolsToRemove > str.length())
            throw new IllegalArgumentException("Number of symbols to remove was specified that's larger than the random string itself.");
        str.delete(str.length() - nOfLastSymbolsToRemove, str.length());
        return str.toString();
    }
}
