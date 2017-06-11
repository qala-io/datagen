package io.qala.datagen.junit5;

import java.util.Arrays;
import java.util.stream.Stream;

class NumericsArgumentProvider extends RandomizedArgumentProvider<Numerics> {
    private Numerics annotation;

    @Override
    public void accept(Numerics annotation) {
        this.annotation = annotation;
    }

    @Override Stream<Object[]> getValueWithDescription() {
        return Arrays.stream(annotation.value()).map(NumericArgumentProvider::generateParams);
    }
    @Override Stream<Object> getValue() {
        return Arrays.stream(annotation.value()).map(NumericArgumentProvider::generateParam);
    }

}
