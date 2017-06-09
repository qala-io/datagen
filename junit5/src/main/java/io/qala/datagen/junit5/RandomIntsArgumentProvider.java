package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.integer;

class RandomIntsArgumentProvider implements ArgumentsProvider, AnnotationConsumer<RandomInts> {
    private RandomInts annotation;

    @Override
    public void accept(RandomInts annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext containerExtensionContext) throws Exception {
        int result[] = new int[annotation.value().length];
        for(int i = 0; i < result.length; i++) {
            RandomInt rInt = annotation.value()[i];
            result[i] = integer(rInt.min(), rInt.max());
        }
        return IntStream.of(result).mapToObj(Arguments::of);
    }
}
