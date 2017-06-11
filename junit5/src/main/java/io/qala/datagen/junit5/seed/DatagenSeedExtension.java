package io.qala.datagen.junit5.seed;

import io.qala.datagen.junit5.DatagenUtils;
import org.junit.jupiter.api.extension.*;

/**
 * Logs the seeds of the failed methods so that it's possible to re-run tests with the same data generated.
 * Unfortunately right now
 * <a href="https://github.com/junit-team/junit5/issues/618">JUnit5 doesn't have means to know which test failed</a>
 * and which passed, so currently the seed is printed for each test method that threw exception.
 *
 * @see io.qala.datagen.Seed
 */
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
