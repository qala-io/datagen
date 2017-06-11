package io.qala.datagen.junit5;

import java.util.Arrays;
import java.util.stream.Stream;

class RandomLongsArgumentProvider extends RandomizedArgumentProvider<RandomLongs> {
    private RandomLongs annotation;

    @Override
    public void accept(RandomLongs annotation) {
        this.annotation = annotation;
    }

    @Override Stream<Object[]> getValueWithDescription() {
        return Arrays.stream(annotation.value()).map(RandomLongArgumentProvider::generateParams);
    }
    @Override Stream<Object> getValue() {
        return Arrays.stream(annotation.value()).map(RandomLongArgumentProvider::generateParam);
    }
}
