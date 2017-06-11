package io.qala.datagen.junit5;

import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.Long;

class RandomLongArgumentProvider extends RandomizedArgumentProvider<RandomLong> {
    private RandomLong annotation;

    @Override
    public void accept(RandomLong annotation) {
        this.annotation = annotation;
    }

    @Override Stream<Object[]> getValueWithDescription() {
        return Stream.of(annotation).map(RandomLongArgumentProvider::generateParams);
    }
    @Override Stream<Object> getValue() {
        return Stream.of(annotation).map(RandomLongArgumentProvider::generateParam);
    }

    static long generateParam(RandomLong annotation) {
        return Long(annotation.min(), annotation.max());
    }

    static Object[] generateParams(RandomLong annotation) {
        String name = annotation.name();
        if(name.isEmpty()) name = "long from " + annotation.min() + " to " + annotation.max();
        return new Object[]{Long(annotation.min(), annotation.max()), name};
    }
}
