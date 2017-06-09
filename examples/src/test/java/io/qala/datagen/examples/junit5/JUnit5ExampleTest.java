package io.qala.datagen.examples.junit5;

import io.qala.datagen.junit5.Alphanumeric;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnit5ExampleTest {
    @Alphanumeric(length = 1, name = "min boundary")
    @Alphanumeric(min = 2, max = 29, name = "middle value")
    @Alphanumeric(length = 30, name = "max boundary")
    void canGenerateMultipleAlphanumerics(String value, String name) {
        assertTrue(value.length() >= 1 && value.length() <= 31);
    }
}
