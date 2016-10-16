package io.qala.datagen.examples;

import org.junit.Test;

import static io.qala.datagen.RandomShortApi.*;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.junit.Assert.assertEquals;

/**
 * If either first name or last name is blank (or both) then in greetings use username. E.g. "Hello, admin".
 * Otherwise greet with last and first name. E.g. "Hello, Mickey Mouse".
 */
public class _10_CombinatorialGreetingsTest {
    @Test public void ifOneOrBoth_firstAndLastName_isBlank_greetWithUsername() {
        String firstName = blankOr(english(1, 10));
        String lastName = isBlank(firstName) ? blankOr(english(10)) : nullOrBlank();

        Person person = new Person(english(1, 10)).firstName(firstName).lastName(lastName);
        assertEquals(person.username(), person.greeting());
    }

    @Test public void ifBoth_firstAndLastNameAreNotBlank_greetWithFirstAndLastName() {
        String firstName = english(1, 10);
        String lastName = english(1, 10);

        Person person = new Person(english(1, 10)).firstName(firstName).lastName(lastName);
        assertEquals(firstName + " " + lastName, person.greeting());
    }

    @Test public void transformsA_toB_andBackwards() {
    }

    @Test public void updatesAllFieldsViaRestApi() {
    }
}
