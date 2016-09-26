package io.qala.datagen.examples;

import org.junit.Test;

import static io.qala.datagen.RandomShortApi.english;
import static io.qala.datagen.RandomShortApi.sample;
import static io.qala.datagen.RandomShortApi.unicode;

public class WhichOneToPickTest {
    @Test public void usernameValidation_passesHappyPath_traditional() {
        new Person("John").validate();
    }
    @Test public void usernameValidation_passesHappyPath_randomization() {
        new Person(english(1, 50)).validate();
    }

    @Test public void countryValidation_passesHappyPath_traditional() {
        new Person("John").country(Country.US).validate();
    }
    @Test public void countryValidation_passesHappyPath_randomization() {
        new Person(english(1, 50)).country(Country.random()).validate();
    }
}