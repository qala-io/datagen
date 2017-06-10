package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.Arrays;
import java.util.stream.Stream;

class EnglishesArgumentProvider implements ArgumentsProvider, AnnotationConsumer<Englishes> {
    private Englishes annotation;

    @Override
    public void accept(Englishes annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext containerExtensionContext) throws Exception {
        if (Utils.injectCaseName(containerExtensionContext))
            return Arrays.stream(annotation.value())
                    .map(EnglishArgumentProvider::generateParams)
                    .map(Arguments::of);
        return Arrays.stream(annotation.value())
                .map(EnglishArgumentProvider::generateParam)
                .map(Arguments::of);
    }

}
