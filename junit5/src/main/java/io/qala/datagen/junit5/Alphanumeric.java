package io.qala.datagen.junit5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Repeatable(Alphanumerics.class)

@ArgumentsSource(AlphanumericArgumentProvider.class)
@ParameterizedTest
public @interface Alphanumeric {
    int min() default 1;
    int max() default 100;
    String name() default "";

    /**
     * Set it if you need a fixed-length string generated. {@link #max()} and {@link #min()} are ignored in that case.
     *
     * @return length of the string to generate
     */
    int length() default -1;
}
