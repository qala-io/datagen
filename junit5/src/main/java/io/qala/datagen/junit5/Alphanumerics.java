package io.qala.datagen.junit5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

@ParameterizedTest
@ArgumentsSource(AlphanumericsArgumentProvider.class)
public @interface Alphanumerics {
    Alphanumeric[] value();
}
