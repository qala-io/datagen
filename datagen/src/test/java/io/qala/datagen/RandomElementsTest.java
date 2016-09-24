package io.qala.datagen;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.qala.datagen.RandomElements.from;
import static io.qala.datagen.RandomShortApi.*;
import static io.qala.datagen.RandomValue.length;
import static io.qala.datagen.RandomValue.upTo;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertThrows;
import static org.junit.gen5.api.Assertions.assertTrue;

@RunWith(JUnit5.class)
@DisplayName("Random Elements")
@SuppressWarnings("unused")
public class RandomElementsTest {
    @Test void canSampleTheOnlyElementOfList() {
        assertEquals("ABC", from("ABC").sample());
    }

    @Test void canSampleOneElementFromList() {
        List<String> list = upTo(10).alphanumerics();
        assertThat(list, hasItem(from(list).sample()));
        assertThat(list, hasItem(sample(list)));
    }

    @Test void canSampleOneElementFromList_andAdditionalVarargs() {
        assertThat("element", equalTo(from(emptyList(), "element").sample()));
        assertThat("element", equalTo(sample(emptyList(), "element")));
    }

    @Test void sampleDoesNotReturnDuplicates_ifWithoutReplacement() {
        List<String> population = length(500).alphanumerics(10);
        List<String> sample = from(population).sample(5);

        assertEquals(sample.size(), new HashSet<>(sample).size());
        assertEquals(sampleMultiple(5, population).size(), new HashSet<>(sample).size());
    }
    @Test void samplingSets_returnsSets() {
        HashSet<String> toSampleFrom = new HashSet<>();
        toSampleFrom.add("a");
        assertThat(sampleMultiple(toSampleFrom), instanceOf(Set.class));
    }

    @Test void canSampleOneElementFromArray() {
        assertThat(from("element1", "element2").sample(2), containsInAnyOrder("element1", "element2"));
        assertThat(sampleMultiple(2, "element1", "element2"), containsInAnyOrder("element1", "element2"));
    }

    @Test void mustThrowIfSampleIsLargerThanPopulation() {
        assertThrows(IllegalArgumentException.class, () -> from("el", "el2").sample(3));
        assertThrows(IllegalArgumentException.class, () -> sampleMultiple(3, "el", "el2"));
    }
    @Test void throwsIfCollectionToSampleFromIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> from().sample(3));
        assertThrows(IllegalArgumentException.class, () -> sampleMultiple(0));
        assertThrows(IllegalArgumentException.class, () -> sample());
    }
    @Test void canSampleMultipleElementsFromList() {
        List<String> population = upTo(10).alphanumerics(5, 10);
        List<String> sample = from(population).sample(5);

        assertEquals(5, sample.size());
        assertThat(population, hasItems(sample.toArray(new String[sample.size()])));
    }
    @Test void samplesDuplicateElements_ifSampleSizeLargerThanPopulation_andSamplingIsWithReplacement() {
        List<String> population = upTo(10).alphanumerics(2);
        List<String> sample = from(population).sampleWithReplacement(5);

        assertEquals(5, sample.size());
        assertThat(population, hasItems(sample.toArray(new String[sample.size()])));
    }

    @Test void nullOrObj_returnsNull_sometimes() {
        for(int i = 0; i < 100; i++) if (nullOr("str") == null) return;
        fail("nullOr() had to return null at least once, but it didn't");
    }
    @Test void nullOrObj_returnsObj_sometimes() {
        for(int i = 0; i < 100; i++) if ("str".equals(nullOr("str"))) return;
        fail("nullOr() had to return Obj at least once, but it didn't");
    }
    @Test void nullOrObj_doesNotReturnAnything_butNullOrObj() {
        String result = nullOr("str");
        assertTrue("str".equals(result) || result == null);
    }
    @Test void nullOrEmpty_doesNotReturnAnything_butNullOrEmptyString() {
        String result = nullOrEmpty();
        assertTrue(result == null || result.isEmpty());
    }
    @Test void nullOrEmpty_returnsEmpty_sometimes() {
        for(int i = 0; i < 100; i++) if ("".equals(nullOrEmpty())) return;
        fail("nullOrEmpty() had to return Empty String at least once, but it didn't");
    }
    @Test void nullOrEmpty_returnsNull_sometimes() {
        for(int i = 0; i < 100; i++) if (nullOrEmpty() == null) return;
        fail("nullOrEmpty() had to return null at least once, but it didn't");
    }
    @Test void nullOrBlank_doesNotReturnAnything_butNullOrBlankString() {
        String result = nullOrBlank();
        assertTrue(result == null || result.trim().isEmpty());
    }
    @Test void nullOrBlank_returnsEmpty_sometimes() {
        for(int i = 0; i < 100; i++) if ("".equals(nullOrBlank())) return;
        fail("nullOrBlank() had to return Empty String at least once, but it didn't");
    }
    @Test void nullOrBlank_returnsNull_sometimes() {
        for(int i = 0; i < 100; i++) if (nullOrBlank() == null) return;
        fail("nullOrBlank() had to return null at least once, but it didn't");
    }
    @Test void nullOrBlank_returnsWhitespaces_sometimes() {
        for(int i = 0; i < 100; i++) {
            String nullOrBlank = nullOrBlank();
            if (nullOrBlank != null && nullOrBlank.contains(" ")) return;
        }
        fail("nullOrBlank() had to return whitespaces at least once, but it didn't");
    }
    @Test void blankOr_doesNotReturnAnything_butBlankOrSpecifiedString() {
        String result = blankOr("str");
        assertTrue(result == null || result.trim().isEmpty() || result.equals("str"));
    }
    @Test void blankOr_returnsEmpty_sometimes() {
        for(int i = 0; i < 100; i++) if ("".equals(blankOr("str"))) return;
        fail("blankOr() had to return Empty String at least once, but it didn't");
    }
    @Test void blankOr_returnsNull_sometimes() {
        for(int i = 0; i < 100; i++) if (blankOr("str") == null) return;
        fail("blankOr() had to return null at least once, but it didn't");
    }
    @Test void blankOr_returnsWhitespaces_sometimes() {
        for(int i = 0; i < 100; i++) {
            String nullOrBlank = blankOr("str");
            if (nullOrBlank != null &&  nullOrBlank.contains(" ")) return;
        }
        fail("blankOr() had to return whitespaces at least once, but it didn't");
    }
    @Test void blankOr_returnsSpecifiedString_sometimes() {
        for(int i = 0; i < 100; i++) if ("str".equals(blankOr("str"))) return;
        fail("blankOr() had to return specified string at least once, but it didn't");
    }
}