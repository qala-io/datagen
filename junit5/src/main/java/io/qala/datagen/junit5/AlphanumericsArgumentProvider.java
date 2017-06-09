package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

class AlphanumericsArgumentProvider implements ArgumentsProvider, AnnotationConsumer<Alphanumerics> {
    private Alphanumerics annotation;

    @Override
    public void accept(Alphanumerics annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext containerExtensionContext) throws Exception {
        int paramCount = containerExtensionContext.getTestMethod().get().getParameterCount();
        Stream.Builder<Object> result = Stream.builder();
        if(paramCount == 2) {
            for (Alphanumeric next : annotation.value())
                result.add(new Object[][]{AlphanumericArgumentProvider.generateParams(next)});
        } else {
            for (Alphanumeric next : annotation.value())
                result.add(AlphanumericArgumentProvider.generateParam(next));
        }
        return result.build().map(Arguments::of);
    }

}
