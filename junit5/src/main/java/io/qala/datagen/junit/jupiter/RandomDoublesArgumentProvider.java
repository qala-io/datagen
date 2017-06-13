package io.qala.datagen.junit.jupiter;

import java.util.Arrays;
import java.util.stream.Stream;

class RandomDoublesArgumentProvider extends RandomizedArgumentProvider<RandomDoubles> {
    private RandomDoubles annotation;

    @Override
    public void accept(RandomDoubles annotation) {
        this.annotation = annotation;
    }

    @Override Stream<Object[]> getValueWithDescription() {
        return Arrays.stream(annotation.value()).map(RandomDoubleArgumentProvider::generateParams);
    }
    @Override Stream<Object> getValue() {
        return Arrays.stream(annotation.value()).map(RandomDoubleArgumentProvider::generateParam);
    }
}
