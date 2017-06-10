package io.qala.datagen.junit5;

import org.junit.jupiter.api.extension.ContainerExtensionContext;

class Utils {
    static boolean injectCaseName(ContainerExtensionContext containerExtensionContext) {
        //noinspection OptionalGetWithoutIsPresent Hm.. the docs doesn't state when the test method may be abscent. Will need to check.
        return containerExtensionContext.getTestMethod().get().getParameterCount() == 2;
    }
}
