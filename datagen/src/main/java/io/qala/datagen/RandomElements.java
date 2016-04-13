package io.qala.datagen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static io.qala.datagen.RandomValue.upTo;

public class RandomElements<T> {
    private final List<T> elements;

    RandomElements(T[] elements) {
        this(Arrays.asList(elements));
    }

    RandomElements(Collection<T> elements) {
        if(elements == null || elements.isEmpty())
            throw new IllegalArgumentException("Can't sample from zero elements: " + elements);
        this.elements = new ArrayList<T>(elements);
    }

    public static <T> RandomElements<T> from(Collection<T> elements, T... others) {
        Collection<T> coll = new ArrayList<>(elements);
        coll.addAll(Arrays.asList(others));
        return new RandomElements<T>(coll);
    }

    public static <T> RandomElements<T> from(T... elements) {
        return new RandomElements<T>(elements);
    }

    public T sample() {
        int index = upTo(size() - 1).integer();
        return this.elements.get(index);
    }
    public List<T> sample(int nToReturn) {
        if(nToReturn > this.elements.size())
            throw new IllegalArgumentException("Sample cannot be larger than the initial collection. " +
                    "If you want to allow the sample to contain duplicates, sample with replacement.");
        List<T> elements = new ArrayList<>(this.elements);
        List<T> result = new ArrayList<T>(nToReturn);
        for(int i = 0; i < nToReturn; i++) {
            int index = upTo(elements.size() - 1).integer();
            result.add(elements.remove(index));
        }
        return result;
    }
    public List<T> sampleWithReplacement(int nToReturn) {
        List<T> result = new ArrayList<T>(nToReturn);
        for(int i = 0; i < nToReturn; i++) {
            int index = upTo(size() - 1).integer();
            result.add(this.elements.get(index));
        }
        return result;
    }

    private int size() {
        return elements.size();
    }
}
