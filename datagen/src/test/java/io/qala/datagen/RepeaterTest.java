package io.qala.datagen;

import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import static io.qala.datagen.RandomString.Type.*;
import static io.qala.datagen.RandomValue.*;
import static io.qala.datagen.Repeater.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertThrows;

@RunWith(JUnit5.class)
public class RepeaterTest {
    @Test void stringsAreRepeated() {
        assertEquals("", repeat("1-").times(0));
        assertEquals("1-1-1", repeat("1-").times(3));
        assertEquals("1-1-1", repeat("1").string("-").times(3));
        assertEquals("1-1-1-", repeat("1").string("-").includeLastSymbol().times(3));
        assertEquals("1-1-", repeat("1").string("-").removeLastSymbols(2).times(3));
    }

    @Test void stringsAndRandomsAreRepeated() {
        assertThat(repeat(length(2), NUMERIC).string("-").times(3), matchesPattern("\\d{2}-\\d{2}-\\d{2}"));
        assertThat(repeat(length(2), ENGLISH).string(" ").times(1, 2), matchesPattern("[a-zA-Z]{2}|[a-zA-Z]{2} [a-zA-Z]{2}"));
        assertThat(repeat(length(2), ENGLISH).string(" ").includeLastSymbol().times(2), matchesPattern("[a-zA-Z]{2} [a-zA-Z]{2} "));
    }

    @Test void throwsIfRepeatingNegativeNumberOfTimes() {
        assertThrows(IllegalArgumentException.class, () -> repeat("").times(-1));
        assertThrows(IllegalArgumentException.class, () -> repeat("").times(-1, 5));
    }
}