package io.qala.datagen;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;

import static io.qala.datagen.RandomElements.from;
import static io.qala.datagen.RandomValue.length;
import static io.qala.datagen.RandomValue.upTo;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertThrows;

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
    }

    @Test void canSampleOneElementFromList_andAdditionalVarargs() {
        assertThat("element", equalTo(from(emptyList(), "element").sample()));
    }

    @Test void sampleDoesNotReturnDuplicates_ifWithoutReplacement() {
        List<String> population = length(500).alphanumerics(10);
        List<String> sample = from(population).sample(5);
        assertEquals(sample.size(), new HashSet<>(sample).size());
    }

    @Test void canSampleOneElementFromArray() {
        assertThat(from("element1", "element2").sample(2), containsInAnyOrder("element1", "element2"));
    }

    @Test void mustThrowIfSampleIsLargerThanPopulation() {
        assertThrows(IllegalArgumentException.class, () -> from("el", "el2").sample(3));
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
}