package io.qala.datagen.junit5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

/**
 * Can pass a random integer to a JUnit5 test.
 * If multiple of these annotations are specified, the test will be run multiple times each time with a different value.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Repeatable(RandomInts.class)

@ArgumentsSource(RandomIntArgumentProvider.class)
@ParameterizedTest
public @interface RandomInt {
    /** Min value of the generated int. */
    int min() default Integer.MIN_VALUE;
    /** Max value of the generated int. */
    int max() default Integer.MAX_VALUE;
    /**
     * Name of the test case, useful when you have multiple annotations and you want to give title to each of the
     * generated string. Can be obtained in the test if there is a second param of type String. Ignored if the 2nd
     * param is not present in the test method.
     */
    String name() default "";
}
