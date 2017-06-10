package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

import static io.qala.datagen.RandomShortApi.Double;

class RandomDoubleArgumentProvider implements ArgumentsProvider, AnnotationConsumer<RandomDouble> {
    private RandomDouble annotation;

    @Override
    public void accept(RandomDouble annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext extensionContext) throws Exception {
        if (Utils.injectCaseName(extensionContext))
            return Stream.of(annotation)
                    .map(RandomDoubleArgumentProvider::generateParams)
                    .map(Arguments::of);
        return Stream.of(annotation)
                .map(RandomDoubleArgumentProvider::generateParam)
                .map(Arguments::of);
    }

    static Double generateParam(RandomDouble annotation) {
        return Double(annotation.min(), annotation.max());
    }

    static Object[] generateParams(RandomDouble annotation) {
        String name = annotation.name();
        if(name.isEmpty()) name = "double from " + annotation.min() + " to " + annotation.max();
        return new Object[]{Double(annotation.min(), annotation.max()), name};
    }
}
