package io.qala.datagen.examples;

import org.junit.Test;

import static io.qala.datagen.RandomShortApi.*;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.junit.Assert.assertEquals;

/**
 * If either first name or last name is blank (or both) then in greetings use username. E.g. "Hello, admin".
 * Otherwise greet with last and first name. E.g. "Hello, Mickey Mouse".
 */
public class CombinatorialGreetingsTest {
    @Test public void ifOneOrBoth_firstAndLastName_isBlank_greetWithUsername() {
        String firstName = sample("", " ", null, unicode(1, 10));
        String lastName = isBlank(firstName) ? sample(nullOrBlank(), unicode(10)) : nullOrBlank();

        Person person = new Person(unicode(1, 10)).firstName(firstName).lastName(lastName);
        assertEquals(person.username(), person.greeting());
    }

    @Test public void ifBoth_firstAndLastNameAreNotBlank_greetWithFirstAndLastName() {
        String firstName = unicode(1, 10);
        String lastName = unicode(1, 10);

        Person person = new Person(unicode(1, 10)).firstName(firstName).lastName(lastName);
        assertEquals(firstName + " " + lastName, person.greeting());
    }

    @Test public void transformsA_toB_andBackwards() {
    }

    @Test public void updatesAllFieldsViaRestApi() {
    }
}
