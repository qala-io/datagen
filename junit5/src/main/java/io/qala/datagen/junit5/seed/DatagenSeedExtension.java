package io.qala.datagen.junit5.seed;

import io.qala.datagen.junit5.DatagenUtils;
import org.junit.jupiter.api.extension.*;

public class DatagenSeedExtension implements BeforeTestExecutionCallback, BeforeAllCallback, TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(TestExtensionContext context, Throwable throwable) throws Throwable {
        context.getTestMethod().ifPresent((m) -> DatagenUtils.logCurrentSeeds(context));
        throw throwable;
    }

    @Override public void beforeTestExecution(TestExtensionContext context) throws Exception {
        context.getTestMethod().ifPresent((m) -> DatagenUtils.setCurrentSeedIfNotSetYet(context));
    }
    @Override public void beforeAll(ContainerExtensionContext context) throws Exception {
        context.getTestClass().ifPresent((m) -> DatagenUtils.setCurrentSeedIfNotSetYet(context));
    }
}
