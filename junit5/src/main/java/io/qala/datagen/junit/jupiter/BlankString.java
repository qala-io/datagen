package io.qala.datagen.junit.jupiter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

/** Passes either {@code null} or empty string or string with spaces to JUnit5 tests. */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})

@ArgumentsSource(BlankStringProvider.class)
@ParameterizedTest
public @interface BlankString {
    /**
     * Is passed to the test case if 2nd parameter is available in the test method. If left blank it's going to be
     * filled with the description of what's passed - null or empty string or string with spaces.
     */
    String name() default "";
}
