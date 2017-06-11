package io.qala.datagen.junit5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

/**
 * Can pass a random english-letters string to a JUnit5 test.
 * If multiple of these annotations are specified, the test will be run multiple times each time with a different value.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Repeatable(Englishes.class)

@ArgumentsSource(EnglishArgumentProvider.class)
@ParameterizedTest
public @interface English {
    /** Min length of the generated string. Ignored if {@link #length()} is specified. */
    int min() default 1;
    /** Max length of the generated string. Ignored if {@link #length()} is specified. */
    int max() default 100;
    /**
     * Set it if you need a fixed-length string generated. {@link #max()} and {@link #min()} are ignored in that case.*/
    int length() default -1;
    /**
     * Name of the test case, useful when you have multiple annotations and you want to give title to each of the
     * generated string. Can be obtained in the test if there is a second param of type String. Ignored if the 2nd
     * param is not present in the test method.
     */
    String name() default "latin letters";
}
