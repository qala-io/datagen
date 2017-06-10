package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.Arrays;
import java.util.stream.Stream;

class UnicodesArgumentProvider implements ArgumentsProvider, AnnotationConsumer<Unicodes> {
    private Unicodes annotation;

    @Override
    public void accept(Unicodes annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext containerExtensionContext) throws Exception {
        if (Utils.injectCaseName(containerExtensionContext))
            return Arrays.stream(annotation.value())
                    .map(UnicodeArgumentProvider::generateParams)
                    .map(Arguments::of);
        return Arrays.stream(annotation.value())
                .map(UnicodeArgumentProvider::generateParam)
                .map(Arguments::of);
    }

}