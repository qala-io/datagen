package io.qala.datagen.junit5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Repeatable(RandomInts.class)

@ArgumentsSource(RandomIntArgumentProvider.class)
@ParameterizedTest
public @interface RandomInt {
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
    String name() default "";
}
