package io.qala.datagen.junit5;

import java.util.Arrays;
import java.util.stream.Stream;

class UnicodesArgumentProvider extends RandomizedArgumentProvider<Unicodes> {
    private Unicodes annotation;

    @Override
    public void accept(Unicodes annotation) {
        this.annotation = annotation;
    }

    @Override Stream<Object[]> getValueWithDescription() {
        return Arrays.stream(annotation.value()).map(UnicodeArgumentProvider::generateParams);
    }
    @Override Stream<Object> getValue() {
        return Arrays.stream(annotation.value()).map(UnicodeArgumentProvider::generateParam);
    }
}
