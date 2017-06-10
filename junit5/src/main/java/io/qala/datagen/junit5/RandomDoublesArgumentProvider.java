package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.Arrays;
import java.util.stream.Stream;

class RandomDoublesArgumentProvider implements ArgumentsProvider, AnnotationConsumer<RandomDoubles> {
    private RandomDoubles annotation;

    @Override
    public void accept(RandomDoubles annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext extensionContext) throws Exception {
        if (Utils.injectCaseName(extensionContext)) {
            return Arrays.stream(annotation.value())
                    .map(RandomDoubleArgumentProvider::generateParams)
                    .map(Arguments::of);
        }
        return Arrays.stream(annotation.value())
                .map(RandomDoubleArgumentProvider::generateParam)
                .map(Arguments::of);
    }
}
