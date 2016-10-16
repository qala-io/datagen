package io.qala.datagen.examples;

import org.junit.Test;

import static io.qala.datagen.RandomShortApi.unicode;

public class _05_MaxBoundaryValidationTest {
    private Db db = new Db();

    @Test public void usernameValidation_passesForMaxBoundary_inTraditionalApproach() {
        db.save(new Person("ABCDEFGHIJАБВГДИЙКЛм"));
    }

    @Test(expected = IllegalStateException.class)
    public void usernameValidation_failsForMaxBoundary_ifRandomized() {//will fail once in a million cases
        db.save(new Person(unicode(20)));
    }
}
