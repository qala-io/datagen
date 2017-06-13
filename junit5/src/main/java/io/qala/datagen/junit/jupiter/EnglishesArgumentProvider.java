package io.qala.datagen.junit.jupiter;

import java.util.Arrays;
import java.util.stream.Stream;

class EnglishesArgumentProvider extends RandomizedArgumentProvider<Englishes> {
    private Englishes annotation;

    @Override
    public void accept(Englishes annotation) {
        this.annotation = annotation;
    }

    @Override Stream<Object[]> getValueWithDescription() {
        return Arrays.stream(annotation.value()).map(EnglishArgumentProvider::generateParams);
    }
    @Override Stream<Object> getValue() {
        return Arrays.stream(annotation.value()).map(EnglishArgumentProvider::generateParam);
    }
}
