package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.Long;

class RandomLongArgumentProvider implements ArgumentsProvider, AnnotationConsumer<RandomLong> {
    private RandomLong annotation;

    @Override
    public void accept(RandomLong annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext extensionContext) throws Exception {
        if (Utils.injectCaseName(extensionContext))
            return Stream.of(annotation)
                    .map(RandomLongArgumentProvider::generateParams)
                    .map(Arguments::of);
        return Stream.of(annotation)
                .map(RandomLongArgumentProvider::generateParam)
                .map(Arguments::of);
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
