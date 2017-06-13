package io.qala.datagen.junit.jupiter;

import java.util.Arrays;
import java.util.stream.Stream;

class AlphanumericsArgumentProvider extends RandomizedArgumentProvider<Alphanumerics> {
    private Alphanumerics annotation;

    @Override
    public void accept(Alphanumerics annotation) {
        this.annotation = annotation;
    }

    @Override Stream<Object[]> getValueWithDescription() {
        return Arrays.stream(annotation.value()).map(AlphanumericArgumentProvider::generateParams);
    }
    @Override Stream<Object> getValue() {
        return Arrays.stream(annotation.value()).map(AlphanumericArgumentProvider::generateParam);
    }
}
