package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.english;
import static io.qala.datagen.RandomShortApi.numeric;

class NumericArgumentProvider implements ArgumentsProvider, AnnotationConsumer<Numeric> {
    private Numeric annotation;

    @Override
    public void accept(Numeric annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext containerExtensionContext) throws Exception {
        if (Utils.injectCaseName(containerExtensionContext))
            return Stream.of(annotation)
                    .map(NumericArgumentProvider::generateParams)
                    .map(Arguments::of);
        return Stream.of(annotation)
                .map(NumericArgumentProvider::generateParam)
                .map(Arguments::of);
    }

    static String generateParam(Numeric annotation) {
        if (annotation.length() > 0) return numeric(annotation.length());
        return numeric(annotation.min(), annotation.max());
    }
    static Object[] generateParams(Numeric annotation) {
        return new Object[]{generateParam(annotation), annotation.name()};
    }
}
