package io.qala.datagen;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.qala.datagen.RandomShortApi.*;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Functional Randomizer")
class RandomShortApiTest {
    @Test void onlyOneFunctionIsCalled() {
        List<Person> people = repeat((p) -> callOneOf(
                () -> p.firstName = english(5),
                () -> p.lastName = english(5))
        );
        people.forEach((p) -> assertOnlyOneIsNull(p.lastName, p.firstName));
    }

    @Test void sometimesNoneOfFunctionsAreCalled() {
        List<Person> people = repeat((p) -> callNoneOrMore(
                () -> p.firstName = english(5),
                () -> p.lastName = english(5))
        );
        assertThat(people, hasItem(equalTo(new Person())));
    }

    @Test void atLeastOneFunctionIsAlwaysCalled() {
        List<Person> people = repeat((p) -> callOneOrMore(
                () -> p.firstName = english(5),
                () -> p.lastName = english(5))
        );
        assertThat(people, not(hasItem(equalTo(new Person()))));
    }

    @Test void atLeastOneElementIsReturned() {
        assertFalse(sampleMultiple(asList(integer(), integer())).isEmpty());
    }

    @Test void mixedCaseReturnsSameStringWithDifferentCases() {
        String original = alphanumeric(200);
        String mixed = mixedCase(original);
        assertNotEquals(original, mixed);
        assertEquals(original.toLowerCase(), mixed.toLowerCase());
    }
    @Test void emptyStringInMixedCaseIsEmptyString() {
        assertEquals("", mixedCase(blankOr("")));
    }

    private void assertOnlyOneIsNull(Object o1, Object o2) {
        if (o1 == null) assertNotNull(o2);
        if (o2 == null) assertNotNull(o1);
    }

    private List<Person> repeat(java.util.function.Consumer<Person> function) {
        List<Person> toReturn = new ArrayList<>();
        for (int i = 0; i < integer(50, 100); i++) {
            Person person = new Person();
            function.accept(person);
            toReturn.add(person);
        }
        return toReturn;
    }

    static class Person {
        String firstName, lastName;

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Person person = (Person) o;
            return Objects.equals(this.firstName, person.firstName) && Objects.equals(this.lastName, person.lastName);
        }

        @Override public int hashCode() {
            return Objects.hash(firstName, lastName);
        }

        @Override public String toString() {
            return "[" + firstName + ", " + lastName + "]";
        }
    }
}