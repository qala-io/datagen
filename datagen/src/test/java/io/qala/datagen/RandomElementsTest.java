package io.qala.datagen;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import java.util.List;

import static io.qala.datagen.RandomElements.from;
import static io.qala.datagen.RandomValue.upTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.gen5.api.Assertions.assertEquals;

@RunWith(JUnit5.class)
@DisplayName("Random Elements")
public class RandomElementsTest {
    @Test void canSampleTheOnlyElementOfList() {
        assertEquals("ABC", from("ABC").sample());
    }

    @Test void canSampleOneElementFromList() {
        List<String> list = upTo(10).alphanumerics();
        assertThat(list, hasItem(from(list).sample()));
    }

    @Test void canSampleMultipleElementsFromList() {
        List<String> population = upTo(10).alphanumerics();
        List<String> sample = from(population).sample(5);

        assertEquals(5, sample.size());
        assertThat(population, hasItems(sample.toArray(new String[sample.size()])));
    }
    @Test void samplesDuplicateElements_ifSampleSizeLargerThanPopulation_andSamplingIsWithReplacement() {
        List<String> population = upTo(10).alphanumerics(2);
        List<String> sample = from(population).sample(5);

        assertEquals(5, sample.size());
        assertThat(population, hasItems(sample.toArray(new String[sample.size()])));
    }
}