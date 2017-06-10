package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.unicode;

class UnicodeArgumentProvider implements ArgumentsProvider, AnnotationConsumer<Unicode> {
    private Unicode annotation;

    @Override
    public void accept(Unicode annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext containerExtensionContext) throws Exception {
        if (Utils.injectCaseName(containerExtensionContext))
            return Stream.of(annotation)
                    .map(UnicodeArgumentProvider::generateParams)
                    .map(Arguments::of);
        return Stream.of(annotation)
                .map(UnicodeArgumentProvider::generateParam)
                .map(Arguments::of);
    }

    static String generateParam(Unicode annotation) {
        if (annotation.length() > 0) return unicode(annotation.length());
        return unicode(annotation.min(), annotation.max());
    }
    static Object[] generateParams(Unicode annotation) {
        return new Object[]{generateParam(annotation), annotation.name()};
    }
}
