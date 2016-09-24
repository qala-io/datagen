package io.qala.datagen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static io.qala.datagen.RandomValue.upTo;

@SuppressWarnings({"Convert2Diamond", "WeakerAccess"})
public class RandomElements<T> {
    private final List<T> elements;

    private RandomElements(T[] elements) {
        this(Arrays.asList(elements));
    }

    private RandomElements(Collection<T> elements) {
        if(elements == null || elements.isEmpty())
            throw new IllegalArgumentException("Can't sample from zero elements: " + elements);
        this.elements = new ArrayList<T>(elements);
    }

    /**
     * In case you have a collection and then couple of other elements you want to sample from too, but you don't want
     * to create a collection that includes all of them combined.
     *
     * @param elements the collection
     * @param others   other elements you'd like to include into population to sample from
     * @return a random element from all the listed elements/other elements
     */
    @SafeVarargs public static <T> RandomElements<T> from(Collection<T> elements, T... others) {
        Collection<T> coll = new ArrayList<T>(elements);
        coll.addAll(Arrays.asList(others));
        return new RandomElements<T>(coll);
    }

    @SafeVarargs public static <T> RandomElements<T> from(T... elements) {
        return new RandomElements<T>(elements);
    }

    /**
     * Returns random element from the collection.
     *
     * @return a random element from the collection
     */
    public T sample() {
        int index = upTo(size() - 1).integer();
        return this.elements.get(index);
    }

    /**
     * Returns random elements from the collection. This is a sampling without replacement. For sampling with
     * replacement see {@link #sampleWithReplacement(int)} - it can return the same element multiple times.
     *
     * @param nToReturn number of items to return from the collection
     * @return random elements of the collection, cannot be larger than the collection size
     * @throws IllegalArgumentException if number of elements requested is larger than the number of available items
     *                                  there. Use {@link #sampleWithReplacement(int)} if you want the same
     *                                  element to be returned multiple times.
     */
    public List<T> sample(int nToReturn) {
        if(nToReturn > this.elements.size())
            throw new IllegalArgumentException("Sample cannot be larger than the initial collection. " +
                    "If you want to allow the sample to contain duplicates, sample with replacement.");
        List<T> elements = new ArrayList<T>(this.elements);
        List<T> result = new ArrayList<T>(nToReturn);
        for(int i = 0; i < nToReturn; i++) {
            int index = upTo(elements.size() - 1).integer();
            result.add(elements.remove(index));
        }
        return result;
    }

    /**
     * Returns random elements of the collection, note that the same item could be returned multiple times (item is
     * getting replaced back to the collection). To sample without replacement (cannot return same item twice) use
     * {@link #sample(int)}.
     *
     * @param nToReturn number of elements from the collection to return - can be larger than the collection size as
     *                  this sampling allows same elements to be returned multiple times
     * @return collection of random elements picked from the initial collection
     */
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
