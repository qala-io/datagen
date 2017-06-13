package io.qala.datagen.junit.jupiter;

import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.unicode;

class UnicodeArgumentProvider extends RandomizedArgumentProvider<Unicode> {
    private Unicode annotation;

    @Override
    public void accept(Unicode annotation) {
        this.annotation = annotation;
    }

    @Override Stream<Object[]> getValueWithDescription() {
        return Stream.of(annotation).map(UnicodeArgumentProvider::generateParams);
    }
    @Override Stream<Object> getValue() {
        return Stream.of(annotation).map(UnicodeArgumentProvider::generateParam);
    }

    static String generateParam(Unicode annotation) {
        if (annotation.length() > 0) return unicode(annotation.length());
        return unicode(annotation.min(), annotation.max());
    }
    static Object[] generateParams(Unicode annotation) {
        return new Object[]{generateParam(annotation), annotation.name()};
    }
}
