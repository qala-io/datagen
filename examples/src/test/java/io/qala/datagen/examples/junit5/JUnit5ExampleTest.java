package io.qala.datagen.examples.junit5;

import io.qala.datagen.junit5.Alphanumeric;
import io.qala.datagen.junit5.English;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JUnit5ExampleTest {
    @Alphanumeric(length = 1, name = "min boundary")
    @Alphanumeric(min = 2, max = 29, name = "middle value")
    @Alphanumeric(length = 30, name = "max boundary")
    @English(max=30, name = "letters without digits")
    void canGenerateMultipleAlphanumerics(String value, String name) {
        assertTrue(value.length() >= 1 && value.length() <= 31, "Failed case: " + name);
    }
}
