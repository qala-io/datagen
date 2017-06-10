package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.english;

class EnglishArgumentProvider implements ArgumentsProvider, AnnotationConsumer<English> {
    private English annotation;

    @Override
    public void accept(English annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext containerExtensionContext) throws Exception {
        if (Utils.injectCaseName(containerExtensionContext))
            return Stream.of(annotation)
                    .map(EnglishArgumentProvider::generateParams)
                    .map(Arguments::of);
        return Stream.of(annotation)
                .map(EnglishArgumentProvider::generateParam)
                .map(Arguments::of);
    }

    static String generateParam(English annotation) {
        if (annotation.length() > 0) return english(annotation.length());
        else return english(annotation.min(), annotation.max());
    }
    static Object[] generateParams(English annotation) {
        return new Object[]{generateParam(annotation), annotation.name()};
    }
}
