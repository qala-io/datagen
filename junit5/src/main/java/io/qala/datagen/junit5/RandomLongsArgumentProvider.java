package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.Arrays;
import java.util.stream.Stream;

class RandomLongsArgumentProvider implements ArgumentsProvider, AnnotationConsumer<RandomLongs> {
    private RandomLongs annotation;

    @Override
    public void accept(RandomLongs annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext extensionContext) throws Exception {
        if (Utils.injectCaseName(extensionContext)) {
            return Arrays.stream(annotation.value())
                    .map(RandomLongArgumentProvider::generateParams)
                    .map(Arguments::of);
        }
        return Arrays.stream(annotation.value())
                .map(RandomLongArgumentProvider::generateParam)
                .map(Arguments::of);
    }
}
