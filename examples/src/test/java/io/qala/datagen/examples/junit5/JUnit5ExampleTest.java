package io.qala.datagen.examples.junit5;

import io.qala.datagen.junit5.Alphanumeric;
import io.qala.datagen.junit5.English;
import io.qala.datagen.junit5.Numeric;
import io.qala.datagen.junit5.Unicode;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JUnit5ExampleTest {
    @Alphanumeric(length = 1, name = "min boundary")
    @Alphanumeric(min = 2, max = 29, name = "middle value")
    @Alphanumeric(length = 30, name = "max boundary")
    @English(max=30)
    @Unicode(max=30)
    @Numeric(max=30)
    void canGenerateMultipleAlphanumerics(String value, String name) {
        assertTrue(value.length() >= 1 && value.length() <= 31, "Failed case: " + name);
    }
}
