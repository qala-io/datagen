package io.qala.datagen.junit.jupiter;

import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.integer;

class RandomIntArgumentProvider extends RandomizedArgumentProvider<RandomInt> {
    private RandomInt annotation;

    @Override
    public void accept(RandomInt annotation) {
        this.annotation = annotation;
    }

    @Override Stream<Object[]> getValueWithDescription() {
        return Stream.of(annotation).map(RandomIntArgumentProvider::generateParams);
    }
    @Override Stream<Object> getValue() {
        return Stream.of(annotation).map(RandomIntArgumentProvider::generateParam);
    }

    static int generateParam(RandomInt annotation) {
        return integer(annotation.min(), annotation.max());
    }

    static Object[] generateParams(RandomInt annotation) {
        String name = annotation.name();
        if(name.isEmpty()) name = "int from " + annotation.min() + " to " + annotation.max();
        return new Object[]{integer(annotation.min(), annotation.max()), name};
    }
}
