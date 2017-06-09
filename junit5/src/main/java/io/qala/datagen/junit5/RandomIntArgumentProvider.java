package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.integer;

class RandomIntArgumentProvider implements ArgumentsProvider, AnnotationConsumer<RandomInt> {
    private RandomInt annotation;

    @Override
    public void accept(RandomInt annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext containerExtensionContext) throws Exception {
        return IntStream.of(integer(annotation.min(), annotation.max())).mapToObj(Arguments::of);
    }
}
