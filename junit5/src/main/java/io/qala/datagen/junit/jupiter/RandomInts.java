package io.qala.datagen.junit.jupiter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

@ParameterizedTest
@ArgumentsSource(RandomIntsArgumentProvider.class)
public @interface RandomInts {
    RandomInt[] value();
}
