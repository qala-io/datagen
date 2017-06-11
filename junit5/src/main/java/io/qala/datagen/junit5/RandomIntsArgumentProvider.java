package io.qala.datagen.junit5;

import java.util.Arrays;
import java.util.stream.Stream;

class RandomIntsArgumentProvider extends RandomizedArgumentProvider<RandomInts> {
    private RandomInts annotation;

    @Override
    public void accept(RandomInts annotation) {
        this.annotation = annotation;
    }

    @Override Stream<Object[]> getValueWithDescription() {
        return Arrays.stream(annotation.value()).map(RandomIntArgumentProvider::generateParams);
    }
    @Override Stream<Object> getValue() {
        return Arrays.stream(annotation.value()).map(RandomIntArgumentProvider::generateParam);
    }
}
