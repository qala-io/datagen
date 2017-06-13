package io.qala.datagen.junit.jupiter;

import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.alphanumeric;

class AlphanumericArgumentProvider extends RandomizedArgumentProvider<Alphanumeric> {
    private Alphanumeric annotation;

    @Override
    public void accept(Alphanumeric annotation) {
        this.annotation = annotation;
    }

    @Override Stream<Object[]> getValueWithDescription() {
        return Stream.of(annotation).map(AlphanumericArgumentProvider::generateParams);
    }
    @Override Stream<Object> getValue() {
        return Stream.of(annotation).map(AlphanumericArgumentProvider::generateParam);
    }

    static String generateParam(Alphanumeric annotation) {
        if (annotation.length() > 0) return alphanumeric(annotation.length());
        else return alphanumeric(annotation.min(), annotation.max());
    }
    static Object[] generateParams(Alphanumeric annotation) {
        return new Object[]{generateParam(annotation), annotation.name()};
    }
}
