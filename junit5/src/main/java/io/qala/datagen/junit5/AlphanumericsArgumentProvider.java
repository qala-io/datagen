package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class AlphanumericsArgumentProvider implements ArgumentsProvider, AnnotationConsumer<Alphanumerics> {
    private Alphanumerics annotation;

    @Override
    public void accept(Alphanumerics annotation) {
        this.annotation = annotation;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ContainerExtensionContext containerExtensionContext) throws Exception {
        Alphanumeric[] annotations = annotation.value();
        if(AlphanumericArgumentProvider.injectCaseName(containerExtensionContext)) {
            List<Object[]> result = new ArrayList<>(annotations.length);
            for (Alphanumeric next : annotations) result.add(AlphanumericArgumentProvider.generateParams(next));
            return result.stream().map(Arguments::of);
        } else {
            List<String> result = new ArrayList<>(annotations.length);
            for (Alphanumeric next : annotations) result.add(AlphanumericArgumentProvider.generateParam(next));
            return result.stream().map(Arguments::of);
        }
    }

}
