package io.qala.datagen.junit.jupiter;

import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.Double;

class RandomDoubleArgumentProvider extends RandomizedArgumentProvider<RandomDouble> {
    private RandomDouble annotation;

    @Override
    public void accept(RandomDouble annotation) {
        this.annotation = annotation;
    }

    @Override Stream<Object[]> getValueWithDescription() {
        return Stream.of(annotation).map(RandomDoubleArgumentProvider::generateParams);
    }
    @Override Stream<Object> getValue() {
        return Stream.of(annotation).map(RandomDoubleArgumentProvider::generateParam);
    }

    static Double generateParam(RandomDouble annotation) {
        return Double(annotation.min(), annotation.max());
    }

    static Object[] generateParams(RandomDouble annotation) {
        String name = annotation.name();
        if(name.isEmpty()) name = "double from " + annotation.min() + " to " + annotation.max();
        return new Object[]{Double(annotation.min(), annotation.max()), name};
    }
}
