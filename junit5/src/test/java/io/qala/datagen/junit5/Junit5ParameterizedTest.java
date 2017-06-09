package io.qala.datagen.junit5;

import static org.junit.jupiter.api.Assertions.*;

public class Junit5ParameterizedTest {
    @RandomInt(min = 1, max = 10)
    void generatesIntInBoundaries(int param) {
        assertTrue(param <= 10);
        assertTrue(param >= 1);
    }

    @RandomInt
    void generatesAnyNumberByDefault(int param) {
    }

    @RandomInt(min = 0, max = 0, name = "zero")
    void canGenerateOneInt(int param) {
        assertEquals(0, param);
    }

    @RandomInt(min = 1, name = "greater than zero")
    @RandomInt(max = -1, name = "less than zero")
    void canGenerateMultipleInts(int param) {
        assertNotEquals(0, param);
    }

    @Alphanumeric
    void generatesAlphanumeric_from1_to100_byDefault(String value) {
        assertTrue(value.trim().length() >= 1);
        assertTrue(value.length() <= 100);
    }

    @Alphanumeric(name = "name")
    void setsNameAsArgumentIf2ndParameterExists(String value, String name) {
        assertEquals("name", name);
    }

    @Alphanumeric(length = 1, name = "min boundary")
    @Alphanumeric(min = 2, max = 29, name = "middle value")
    @Alphanumeric(length = 30, name = "max boundary")
    void canGeneratesMultipleAlphanumerics(String value) {
        assertTrue(value.length() >= 1 && value.length() <= 31);
    }

}
