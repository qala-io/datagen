package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.integer;

class RandomIntArgumentProvider implements ArgumentsProvider, AnnotationConsumer<RandomInt> {
    private RandomInt annotation;

    @Override
    public void accept(RandomInt annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext extensionContext) throws Exception {
        if (Utils.injectCaseName(extensionContext))
            return Stream.of(annotation)
                    .map(RandomIntArgumentProvider::generateParams)
                    .map(Arguments::of);
        return Stream.of(annotation)
                .map(RandomIntArgumentProvider::generateParam)
                .map(Arguments::of);
    }

    static int generateParam(RandomInt annotation) {
        return integer(annotation.min(), annotation.max());
    }

    static Object[] generateParams(RandomInt annotation) {
        return new Object[]{integer(annotation.min(), annotation.max()), annotation.name()};
    }
}
